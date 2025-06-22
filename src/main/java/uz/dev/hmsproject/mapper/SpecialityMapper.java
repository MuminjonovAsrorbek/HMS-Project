package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.entity.Speciality;
import uz.dev.hmsproject.mapper.template.BaseMapper;

import java.util.List;

@Component
public class SpecialityMapper implements BaseMapper<Speciality, SpecialityDTO> {

    @Override
    public Speciality toEntity(SpecialityDTO specialityDTO) {
        return new Speciality(
                specialityDTO.getName()
        );
    }

    @Override
    public  SpecialityDTO toDTO(Speciality speciality) {
        return new SpecialityDTO(
                speciality.getId(),
                speciality.getName()
        );
    }

    @Override
    public List<SpecialityDTO> toDTO(List<Speciality> dtos) {
        return List.of();
    }
}
