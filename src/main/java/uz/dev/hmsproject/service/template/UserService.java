package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.UserFilterDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.dto.response.RespUserDTO;

import java.util.List;


public interface UserService {


    void create(UserDTO userDTO);

    void update(Long id, UserDTO userDTO);

    void delete(Long id);

    RespUserDTO getById(Long id);

    PageableDTO getAllPaginated(Integer page, Integer size);

    List<RespUserDTO> filter(UserFilterDTO filterDTO);

    void changeActive(Long id, boolean active);
}
