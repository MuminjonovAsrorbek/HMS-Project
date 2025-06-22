package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;


public interface UserService extends BaseService<UserDTO, Long> {

    PageableDTO getAllPaginated(Integer page, Integer size);
}
