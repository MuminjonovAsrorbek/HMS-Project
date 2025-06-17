package uz.dev.hmsproject.mapper;

import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.mapper.template.BaseMapper;

/**
 * Created by: asrorbek
 * DateTime: 6/17/25 15:05
 **/

public class UserMapper implements BaseMapper<User, UserDTO> {

    //to -do Userni Dto ga o'girish va entityga o'girish logikasi shu yerda bo'lishi kerak

    @Override
    public User toEntity(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO toDTO(User user) {
        return null;
    }
}
