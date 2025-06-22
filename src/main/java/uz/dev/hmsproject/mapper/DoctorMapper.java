package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.DoctorDTO;
import uz.dev.hmsproject.entity.Doctor;
import uz.dev.hmsproject.mapper.template.BaseMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorMapper implements BaseMapper<Doctor, DoctorDTO> {


    @Override
    public Doctor toEntity(DoctorDTO doctorDTO) {

     return null;

    }

    @Override
    public DoctorDTO toDTO(Doctor doctor) {

        return new DoctorDTO(
                doctor.getId(),
                doctor.getUser().getId(),
                doctor.getSpeciality().getId(),
                doctor.getRoom().getId()
        );
    }

    @Override
    public List<DoctorDTO> toDTO(List<Doctor> dtos) {
        return List.of();
    }


}
