package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;


public interface SpecialityService extends BaseService<SpecialityDTO, Long> {

    PageableDTO getAllByPage(Integer page, Integer size);

}
