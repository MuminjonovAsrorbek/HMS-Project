package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.UserFilterDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;

import java.util.List;


public interface UserService extends BaseService<UserDTO, Long> {

    PageableDTO getAllPaginated(Integer page, Integer size);

    List<UserDTO> filter(UserFilterDTO filterDTO);
}
