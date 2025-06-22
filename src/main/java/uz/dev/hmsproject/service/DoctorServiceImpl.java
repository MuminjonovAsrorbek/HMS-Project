package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.entity.*;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.mapper.DoctorMapper;
import uz.dev.hmsproject.repository.*;
import uz.dev.hmsproject.service.template.DoctorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final UserRepository userRepository;

    private final SpecialityRepository specialityRepository;

    private final RoomRepository roomRepository;

    private final WorkSchedulerRepository workSchedulerRepository;

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorDTO> getAll() {
        // pageableDTO qaytarish kerak
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDTO).toList();
    }

    @Override
    public DoctorDTO getById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        return doctorMapper.toDTO(doctor);
    }

    @Transactional
    @Override
    public void create(DoctorDTO doctorDTO) {
        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public void update(Long id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        updateDoctor(doctorDTO, doctor, userRepository, specialityRepository, roomRepository);

    }

    @Transactional
    @Override
    public void delete(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        doctorRepository.delete(doctor);
    }

    public static void updateDoctor(DoctorDTO doctorDTO, Doctor doctor, UserRepository userRepository, SpecialityRepository specialityRepository, RoomRepository roomRepository) {
        User user = userRepository.findById(doctorDTO.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("user not found by id: " + doctorDTO.getUserId(), HttpStatus.NOT_FOUND));

        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId()).orElseThrow(() ->
                new EntityNotFoundException("speciality not found by id: " + doctorDTO.getSpecialityId(), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(doctorDTO.getRoomId()).orElseThrow(() ->
                new EntityNotFoundException("room not found by id: " + doctorDTO.getRoomId(), HttpStatus.NOT_FOUND));

        doctor.setUser(user);
        doctor.setSpeciality(speciality);
        doctor.setRoom(room);
    }


    @Override
    public List<LocalTime> getAvailable20MinuteSlots(Long doctorId, LocalDate date) {

        int slotDurationMinutes = 20;

        int dayOfWeak = date.getDayOfWeek().getValue();

        WorkScheduler workScheduler = workSchedulerRepository.findByUserIdAndDayOfWeek(doctorId, dayOfWeak)
                .orElseThrow(() -> new EntityNotFoundException("Work schedule not found for doctor id: " + doctorId + " on date: " + date, HttpStatus.NOT_FOUND));

        LocalTime startTime = workScheduler.getStartTime();
        LocalTime endTime = workScheduler.getEndTime();

        List<LocalTime> booked = appointmentRepository.findByDoctorIdAndDateTime(doctorId, date)
                .stream()
                .map(appointment -> appointment.getDateTime().toLocalTime())
                .toList();

        List<LocalTime> slots = new ArrayList<>();
        for (LocalTime time = startTime; !time.plusMinutes(slotDurationMinutes).isAfter(endTime); time = time.plusMinutes(slotDurationMinutes)) {
            if (!booked.contains(time)) {
                slots.add(time);
            }
        }

        return slots;
    }

}
