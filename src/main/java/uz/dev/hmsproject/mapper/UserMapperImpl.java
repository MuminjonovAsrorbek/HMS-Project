package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.mapper.template.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

import static uz.dev.hmsproject.utils.CommonUtils.getOrDef;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.isActive());
        if (userDTO.getRoleId() != null) {
            Role role = new Role();
            role.setId(userDTO.getRoleId());
            user.setRole(role);
        }
        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(getOrDef(user.getId(), null));
        userDTO.setFullName(getOrDef(user.getFullName(), null));
        userDTO.setUsername(getOrDef(user.getUsername(), null));
        userDTO.setPassword(getOrDef(user.getPassword(), null));
        userDTO.setActive(getOrDef(user.isActive(), null));
        userDTO.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        return userDTO;
    }

    @Override
    public List<UserDTO> toDTO(List<User> dtos) {
        return dtos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
