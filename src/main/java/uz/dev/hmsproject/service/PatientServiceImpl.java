package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.response.AppointmentRespDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Appointment;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.mapper.AppointmentMapper;
import uz.dev.hmsproject.repository.AppointmentRepository;
import uz.dev.hmsproject.specification.PatientSpecification;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.dto.PatientSearchDTO;
import uz.dev.hmsproject.entity.Patient;
import uz.dev.hmsproject.exception.DuplicatePhoneNumberException;
import uz.dev.hmsproject.mapper.PatientMapper;
import uz.dev.hmsproject.repository.PatientRepository;
import uz.dev.hmsproject.service.template.PatientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<PatientDTO> getAll() {

        List<Patient> patients = patientRepository.findAll();

        return patientMapper.toDTO(patients);

    }

    @Override
    public PatientDTO getById(Long id) {

        Patient patient = patientRepository.findByIdOrThrow(id);

        return patientMapper.toDTO(patient);

    }

    @Override
    @Transactional
    public void create(PatientDTO patientDTO) {

        if (patientRepository.existsByPhoneNumber(patientDTO.getPhoneNumber())) {

            throw new DuplicatePhoneNumberException(patientDTO.getPhoneNumber());

        }

        Patient patient = patientMapper.toEntity(patientDTO);

        patientRepository.save(patient);
    }


    @Override
    @Transactional
    public void update(Long id, PatientDTO patientDTO) {

        Patient patient = patientRepository.findByIdOrThrow(id);

        Patient updatePatient = patientMapper.updatePatient(patientDTO, patient);

        patientRepository.save(updatePatient);
    }


    @Override
    @Transactional
    public void delete(Long id) {

        Patient patient = patientRepository.findByIdOrThrow(id);

        patientRepository.delete(patient);

    }

    @Override
    public List<PatientDTO> search(PatientSearchDTO searchDTO) {

        return patientRepository.findAll(
                        PatientSpecification.build(
                                searchDTO.getFullName(),
                                searchDTO.getPhoneNumber()
                        )
                ).stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public PageableDTO getPatientHistory(Long patientId, Integer page) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, 10, sort);

        Page<Appointment> appointmentPage = appointmentRepository.findAllByPatientId(patientId, pageable);

        List<Appointment> appointments = appointmentPage.getContent();

        List<AppointmentRespDTO> appointmentRespDTOS = appointmentMapper.toRespDTO(appointments);

        return new PageableDTO(
                appointmentPage.getSize(),
                appointmentPage.getTotalElements(),
                appointmentPage.getTotalPages(),
                !appointmentPage.isLast(),
                !appointmentPage.isFirst(),
                appointmentRespDTOS
        );
    }
}