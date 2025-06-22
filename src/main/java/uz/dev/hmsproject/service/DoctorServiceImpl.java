package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.entity.Doctor;
import uz.dev.hmsproject.entity.Room;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.exception.*;
import uz.dev.hmsproject.mapper.DoctorMapper;
import uz.dev.hmsproject.repository.DoctorRepository;
import uz.dev.hmsproject.repository.RoomRepository;
import uz.dev.hmsproject.repository.SpecialityRepository;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.service.template.DoctorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final SpecialityRepository specialityRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<DoctorDTO> getAll() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDTO).toList();
    }

    @Override
    public DoctorDTO getById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException("doctor not found by id: " + id, HttpStatus.NOT_FOUND));
        return doctorMapper.toDTO(doctor);
    }

    @Transactional
    @Override
    public void create(DoctorDTO doctorDTO) {
        User user = userRepository.findById(doctorDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " +
                        doctorDTO.getUserId(), HttpStatus.NOT_FOUND));

        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId()).orElseThrow(() ->
                new SpecialityNotFoundException("Speciality not found with id: " +
                        doctorDTO.getSpecialityId(), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(doctorDTO.getRoomId()).orElseThrow(() ->
                new RoomNotFoundException("Room not found with id " +
                        doctorDTO.getRoomId(), HttpStatus.NOT_FOUND));




        doctorRepository.findByUser(user).ifPresent(doctor -> {
            throw new DoctorAlreadyExistsException("Doctor already exists for user id: " +
                    doctorDTO.getUserId(), HttpStatus.CONFLICT);
        });

        doctorRepository.findByRoom(room).ifPresent(doctor -> {
            throw new DoctorAlreadyExistsException("Doctor already exists for room id: " +
                    doctorDTO.getRoomId(), HttpStatus.CONFLICT);
        });

        doctorRepository.findBySpeciality(speciality).ifPresent(doctor -> {
            throw new DoctorAlreadyExistsException("Doctor already exists for speciality id: " +
                    doctorDTO.getSpecialityId(), HttpStatus.CONFLICT);
        });



        Doctor doctor = new Doctor(user, speciality, room);



        doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public void update(Long id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException("doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        updateDoctor(doctorDTO, doctor, userRepository, specialityRepository, roomRepository, doctorRepository);

    }

    @Transactional
    @Override
    public void delete(Long id) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException("doctor not found by id: " + id, HttpStatus.NOT_FOUND));

        doctorRepository.delete(doctor);
    }

    public static void updateDoctor(DoctorDTO doctorDTO,
                                    Doctor doctor,
                                    UserRepository userRepository,
                                    SpecialityRepository specialityRepository,
                                    RoomRepository roomRepository,
                                    DoctorRepository doctorRepository) {


        User user = userRepository.findById(doctorDTO.getUserId()).orElseThrow(() ->
                new UserNotFoundException("user not found by id: " + doctorDTO.getUserId(), HttpStatus.NOT_FOUND));

        Speciality speciality = specialityRepository.findById(doctorDTO.getSpecialityId()).orElseThrow(() ->
                new SpecialityNotFoundException("speciality not found by id: " + doctorDTO.getSpecialityId(), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(doctorDTO.getRoomId()).orElseThrow(() ->
                new RoomNotFoundException("room not found by id: " + doctorDTO.getRoomId(), HttpStatus.NOT_FOUND));

        doctorRepository.findByUser(user).ifPresent(doctor1 -> {
            throw new DoctorAlreadyExistsException("Doctor already exists for user id: " + doctorDTO.getUserId(), HttpStatus.CONFLICT);
        });

        doctorRepository.findByUser(user)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("Another doctor already exists for user id: "
                            + doctorDTO.getUserId(), HttpStatus.CONFLICT);
                });

        doctorRepository.findBySpeciality(speciality)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("Another doctor already exists for speciality id: "
                            + doctorDTO.getSpecialityId(), HttpStatus.CONFLICT);
                });

        doctorRepository.findByRoom(room)
                .filter(existing -> !existing.getId().equals(doctor.getId()))
                .ifPresent(d -> {
                    throw new DoctorAlreadyExistsException("Another doctor already exists for room id: "
                            + doctorDTO.getRoomId(), HttpStatus.CONFLICT);
                });


        doctor.setUser(user);
        doctor.setSpeciality(speciality);
        doctor.setRoom(room);

        doctorRepository.save(doctor);

    }


}