package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.enums.Permissions;

import java.util.List;

public interface RoleService extends BaseService<RoleDTO, Long> {

    PageableDTO getAllPaginated(Integer page, Integer size);

    List<Permissions> getAllPermissions();

    List<Permissions> getPermissionsById(Long id);
}
