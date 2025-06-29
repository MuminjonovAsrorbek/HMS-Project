package uz.dev.hmsproject.mapper.template;

import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.response.RespUserDTO;
import uz.dev.hmsproject.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper <User, UserDTO> {


    RespUserDTO toRespDTO(User user);


    List<RespUserDTO> toRespDTO(List<User> dtos);
}
