package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.exception.RoleIsNotFoundException;
import uz.dev.hmsproject.mapper.template.BaseMapper;
import uz.dev.hmsproject.repository.RoleRepository;

import javax.management.relation.RoleInfoNotFoundException;
import javax.management.relation.RoleNotFoundException;
import javax.management.relation.RoleStatus;

/**
 * Created by: asrorbek
 * DateTime: 6/17/25 15:05
 **/

@Component
@RequiredArgsConstructor
public class UserMapper implements BaseMapper<User, UserDTO> {

    private final RoleRepository roleRepository;

    @Override
    public User toEntity(UserDTO userDTO) {
        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(() ->
                new RoleIsNotFoundException("Role not found by id: " + userDTO.getId(),
                        HttpStatus.NOT_FOUND));


        return new User(
                userDTO.getFullName(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                role,
                userDTO.isActive()
        );
    }

    @Override
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().getId(),
                user.isActive()
        );
    }
}
