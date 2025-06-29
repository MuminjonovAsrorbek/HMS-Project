package uz.dev.hmsproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.dto.DoctorFilterDTO;
import uz.dev.hmsproject.dto.response.DoctorResponseDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.AppointmentStatus;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.EntityUniqueException;
import uz.dev.hmsproject.mapper.DoctorMapper;
import uz.dev.hmsproject.repository.*;
import uz.dev.hmsproject.service.template.DoctorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final UserRepository userRepository;

    private final SpecialityRepository specialityRepository;

    private final RoomRepository roomRepository;

    private final EntityManager entityManager;

    private final WorkSchedulerRepository workSchedulerRepository;

    private final AppointmentRepository appointmentRepository;

    @Override
    public PageableDTO getAllPaginated(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Doctor> doctorsPage = doctorRepository.findAll(pageable);

        List<Doctor> doctors = doctorsPage.getContent();

        List<DoctorResponseDTO> doctorDTOS = doctorMapper.toDTO(doctors);

        return new PageableDTO(
                doctorsPage.getSize(),
                doctorsPage.getTotalElements(),
                doctorsPage.getTotalPages(),
                !doctorsPage.isLast(),
                !doctorsPage.isFirst(),
                doctorDTOS
        );
    }

    @Override
    public List<DoctorResponseDTO> filter(DoctorFilterDTO filterDTO) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = criteriaBuilder.createQuery(Doctor.class);
        Root<Doctor> root = criteriaQuery.from(Doctor.class);

        Join<Object, Object> userJoin = root.join(Doctor.Fields.user);
        Join<Object, Object> specialityJoin = root.join(Doctor.Fields.speciality);

        List<Predicate> predicates = new ArrayList<>();

        if (filterDTO.getFullName() != null && !filterDTO.getFullName().isBlank()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(userJoin.get(User.Fields.fullName)),
                    "%" + filterDTO.getFullName().toLowerCase() + "%"
            ));
        }

        if (filterDTO.getUsername() != null && !filterDTO.getUsername().isBlank()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(userJoin.get(User.Fields.username)),
                    "%" + filterDTO.getUsername().toLowerCase() + "%"
            ));
        }

        if (filterDTO.getSpecialityName() != null && !filterDTO.getSpecialityName().isBlank()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(specialityJoin.get(Speciality.Fields.name)),
                    "%" + filterDTO.getSpecialityName().toLowerCase() + "%"
            ));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Doctor> doctors = entityManager.createQuery(criteriaQuery).getResultList();

        return doctorMapper.toDTO(doctors);
    }


    @Override
    public DoctorResponseDTO getById(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        return doctorMapper.toResponseDTO(doctor);

    }

    @Transactional
    @Override
    public void create(DoctorDTO doctorDTO) {

        User user = userRepository.findById(doctorDTO.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User not found by id: " +
                        doctorDTO.getUserId(), HttpStatus.NOT_FOUND));

        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId()).orElseThrow(() ->
                new EntityNotFoundException("Speciality not found by id: " +
                        doctorDTO.getSpecialityId(), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(doctorDTO.getRoomId()).orElseThrow(() ->
                new EntityNotFoundException("Room not found by id: " +
                        doctorDTO.getRoomId(), HttpStatus.NOT_FOUND));


        doctorRepository.findByUser(user).ifPresent(doctor -> {
            throw new EntityUniqueException("Doctor already exists for user id: " +
                    doctorDTO.getUserId(), HttpStatus.CONFLICT);
        });

        doctorRepository.findBySpeciality(speciality).ifPresent(doctor -> {
            throw new EntityUniqueException("Doctor already exists for speciality id: " +
                    doctorDTO.getSpecialityId(), HttpStatus.CONFLICT);
        });

        doctorRepository.findByRoom(room).ifPresent(doctor -> {
            throw new EntityUniqueException("Doctor already exists for room id: " +
                    doctorDTO.getRoomId(), HttpStatus.CONFLICT);
        });


        Doctor doctor = new Doctor(user, speciality, room);

        doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public void update(Long id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        updateDoctor(doctorDTO, doctor, userRepository, specialityRepository, roomRepository, doctorRepository);

    }

    @Transactional
    @Override
    public void delete(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        doctorRepository.delete(doctor);
    }

    private void updateDoctor(DoctorDTO doctorDTO, Doctor doctor, UserRepository userRepository, SpecialityRepository specialityRepository, RoomRepository roomRepository, DoctorRepository doctorRepository) {
        User user = userRepository.findById(doctorDTO.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("user not found by id: " + doctorDTO.getUserId(), HttpStatus.NOT_FOUND));

        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId()).orElseThrow(() ->
                new EntityNotFoundException("speciality not found by id: " + doctorDTO.getSpecialityId(), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(doctorDTO.getRoomId()).orElseThrow(() ->
                new EntityNotFoundException("room not found by id: " + doctorDTO.getRoomId(), HttpStatus.NOT_FOUND));


        doctorRepository.findByUser(user)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new EntityUniqueException("Another doctor already exists for user id: " +
                            doctorDTO.getUserId(), HttpStatus.CONFLICT);
                });


        doctorRepository.findBySpeciality(speciality)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new EntityUniqueException("Another doctor already exists for speciality id: " +
                            doctorDTO.getSpecialityId(), HttpStatus.CONFLICT);
                });


        doctorRepository.findByRoom(room)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new EntityUniqueException("Another doctor already exists for room id: " +
                            doctorDTO.getRoomId(), HttpStatus.CONFLICT);
                });


        doctor.setUser(user);
        doctor.setSpeciality(speciality);
        doctor.setRoom(room);
        doctorRepository.save(doctor);
    }


    @Override
    public List<LocalTime> getAvailable20MinuteSlots(Long doctorId, LocalDate date) {

        int slotDurationMinutes = 20;
        int dayOfWeek = date.getDayOfWeek().getValue(); // 1 = Monday

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId, HttpStatus.NOT_FOUND));

        WorkScheduler workSchedule = workSchedulerRepository
                .findByUserIdAndDayOfWeek(doctor.getUser().getId(), dayOfWeek)
                .orElseThrow(() -> new EntityNotFoundException("Work schedule not found Doctor", HttpStatus.NOT_FOUND));

        LocalTime startTime = workSchedule.getStartTime();
        LocalTime endTime = workSchedule.getEndTime();

        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.atTime(LocalTime.MAX);

        List<Appointment> bookedAppointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateTimeBetweenAndStatusNot(doctorId, from, to, AppointmentStatus.CANCELED);

        Set<LocalTime> bookedTimes = bookedAppointments.stream()
                .map(appointment -> appointment.getAppointmentDateTime().toLocalTime()
                        .truncatedTo(ChronoUnit.MINUTES))
                .collect(Collectors.toSet());

        List<LocalTime> availableSlots = new ArrayList<>();
        for (LocalTime time = startTime; !time.plusMinutes(slotDurationMinutes).isAfter(endTime); time = time.plusMinutes(slotDurationMinutes)) {
            if (!bookedTimes.contains(time)) {
                availableSlots.add(time);
            }
        }

        return availableSlots;
    }

}