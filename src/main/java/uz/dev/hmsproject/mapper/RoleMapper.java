package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.mapper.template.BaseMapper;

import java.util.List;

import static uz.dev.hmsproject.utils.CommonUtils.getOrDef;

@Component
public class RoleMapper implements BaseMapper<Role, RoleDTO> {

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());
        return role;
    }

    @Override
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(getOrDef(role.getId(), null));
        roleDTO.setName(getOrDef(role.getName(), null));
        roleDTO.setPermissions(getOrDef(role.getPermissions(), null));
        return roleDTO;
    }

    @Override
    public List<RoleDTO> toDTO(List<Role> dtos) {
        return dtos.stream().map(this::toDTO).toList();
    }
}
