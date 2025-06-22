package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;

public interface RoleService extends BaseService<RoleDTO, Long> {

    PageableDTO getAllPaginated(Integer page, Integer size);
}
