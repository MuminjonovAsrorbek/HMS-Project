package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.exception.UserAlreadyExistsWithUsernameException;
import uz.dev.hmsproject.exception.UserNotFoundException;
import uz.dev.hmsproject.mapper.UserMapperImpl;
import uz.dev.hmsproject.repository.RoleRepository;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.service.template.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapperImpl userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    public PageableDTO getAllPaginated(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> usersPage = userRepository.findAll(pageable);

        List<User> users = usersPage.getContent();

        List<UserDTO> userDTOS = userMapper.toDTO(users);

        return new PageableDTO(
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                !usersPage.isLast(),
                !usersPage.isFirst(),
                userDTOS
        );
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

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(
                        () -> new UserNotFoundException(userDTO.getRoleId(), HttpStatus.NOT_FOUND)
                );

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        userRepository.save(user);

    }

    @Override
    public void update(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id, HttpStatus.NOT_FOUND)
        );

        if (userRepository.existsByUsername(userDTO.getUsername()) && !user.getUsername().equals(userDTO.getUsername())) {

            throw new UserAlreadyExistsWithUsernameException(userDTO.getUsername(), HttpStatus.CONFLICT);

        }

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(
                        () -> new UserNotFoundException(userDTO.getRoleId(), HttpStatus.NOT_FOUND)
                );

        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id, HttpStatus.CONFLICT);
        }

        // to - do => User databasedan o'chrilmasligi kerak

        userRepository.deleteById(optionalUser.get().getId());
    }

} 
