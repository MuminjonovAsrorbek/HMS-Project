package uz.dev.hmsproject.service.template;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.dev.hmsproject.dto.UserDTO;


public interface UserService extends BaseService<UserDTO, Long> {

    Page<UserDTO> getAllPaginated(Pageable pageable);
}
