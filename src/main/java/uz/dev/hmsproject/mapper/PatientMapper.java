package uz.dev.hmsproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import uz.dev.hmsproject.dto.PatientDTO;
import uz.dev.hmsproject.entity.Patient;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 14:23
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    PatientDTO toDTO(Patient patient);

    List<PatientDTO> toDTO(List<Patient> patients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Patient updatePatient(PatientDTO patientDTO, @MappingTarget Patient patient);



}
