package uz.dev.hmsproject.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.exception.UserAlreadyExistsWithUsernameException;
import uz.dev.hmsproject.exception.UserNotFoundException;
import uz.dev.hmsproject.mapper.UserMapperImpl;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.service.template.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapperImpl userMapper;

    @Override
    public Page<UserDTO> getAllPaginated(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userMapper::toDTO);
    }

    @Override
    public List<UserDTO> getAll() {

        List<User> users = userRepository.findAll();

        return userMapper.toDTO(users);
    }

    @Override
    public UserDTO getById(Long id) {

        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id, HttpStatus.NOT_FOUND)));
    }

    @Override
    public void create(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsWithUsernameException(userDTO.getUsername(), HttpStatus.CONFLICT);
        }
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            User user = userMapper.toEntity(userDTO);
            user.setId(id);
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id, HttpStatus.CONFLICT);
        }
        userRepository.deleteById(optionalUser.get().getId());
    }

} 
