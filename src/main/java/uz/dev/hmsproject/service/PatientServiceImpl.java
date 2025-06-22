package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.entity.Patient;
import uz.dev.hmsproject.exception.DuplicatePhoneNumberException;
import uz.dev.hmsproject.exception.PatientNotFoundException;
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
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id,HttpStatus.BAD_REQUEST));
        return patientMapper.toDTO(patient);
    }

    @Override
    public void create(PatientDTO patientDTO) {
        if (patientRepository.existsByPhoneNumber(patientDTO.getPhoneNumber())) {
            throw new DuplicatePhoneNumberException(patientDTO.getPhoneNumber());
        }

        Patient patient = patientMapper.toEntity(patientDTO);
        patientRepository.save(patient);
    }


    @Override
    public void update(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Cannot update. Patient not found with id: " + id,HttpStatus.BAD_REQUEST));

        patient.setFullName(patientDTO.getFullName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());
        patient.setAddress(patientDTO.getAddress());

        patientRepository.save(patient);
    }

    @Override
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Cannot delete. Patient not found with id: " + id, HttpStatus.BAD_REQUEST);
        }

        patientRepository.deleteById(id);

    }
}
