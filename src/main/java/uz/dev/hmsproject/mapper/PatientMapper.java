package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.entity.Patient;
import uz.dev.hmsproject.mapper.template.BaseMapper;

/**
 * Created by:suhrob
 */

@Component
public class PatientMapper implements BaseMapper<Patient, PatientDTO> {

    @Override
    public PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;
        return new PatientDTO(
                patient.getId(),
                patient.getFullName(),
                patient.getBirthDate(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                patient.getCreatedAt()
        );
    }

    @Override
    public Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;
        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setFullName(dto.getFullName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setAddress(dto.getAddress());
        patient.setCreatedAt(dto.getCreatedAt());
        return patient;
    }
}
