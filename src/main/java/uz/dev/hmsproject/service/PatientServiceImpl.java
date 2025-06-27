package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.Specification.PatientSpecification;
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
}