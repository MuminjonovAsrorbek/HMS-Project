package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.SpecialityCreationDTO;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;


public interface SpecialityService  {

    PageableDTO getAllByPage(Integer page, Integer size);

    SpecialityDTO getById(Long id);

    void create(SpecialityCreationDTO creationDTO);

    void update(Long id, SpecialityDTO specialityDTO);

    void delete(Long id);

}
