package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.service.template.UserService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/17/25 13:32
 **/

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDTO> getAll() {
        return List.of();
    }

    @Override
    public UserDTO getById(Long aLong) {
        return null;
    }

    @Override
    public void create(UserDTO userDTO) {

    }

    @Override
    public void update(Long aLong, UserDTO userDTO) {

    }

    @Override
    public void delete(Long aLong) {

    }

}
