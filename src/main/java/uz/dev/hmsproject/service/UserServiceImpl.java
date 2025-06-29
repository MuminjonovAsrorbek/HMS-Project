package uz.dev.hmsproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.UserFilterDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.UserAlreadyExistsWithUsernameException;
import uz.dev.hmsproject.mapper.UserMapperImpl;
import uz.dev.hmsproject.repository.RoleRepository;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.service.template.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapperImpl userMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final EntityManager manager;

    @Override
    public PageableDTO getAllPaginated(Integer page, Integer size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

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
    public List<UserDTO> filter(UserFilterDTO filterDTO) {
        if (filterDTO == null) {
            return getAll();
        }
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filterDTO.getSearch() != null) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(rootUser.get("fullName"), "%" + filterDTO.getSearch() + "%"),
                    criteriaBuilder.like(rootUser.get("username"), "%" + filterDTO.getSearch() + "%")
            ));
        }

        if (filterDTO.getFullName() != null) {
            predicates.add(criteriaBuilder.like(rootUser.get("fullName"), "%" + filterDTO.getFullName() + "%"));
        }

        if (filterDTO.getUsername() != null) {
            predicates.add(criteriaBuilder.like(rootUser.get("username"), "%" + filterDTO.getUsername() + "%"));
        }

        if (filterDTO.getId() != null) {
            predicates.add(criteriaBuilder.equal(rootUser.get("id"), filterDTO.getId()));
        }

        if (filterDTO.isActive()) {
            predicates.add(criteriaBuilder.equal(rootUser.get("isActive"), filterDTO.isActive()));
        }

        if (filterDTO.getRole() != null) {
            predicates.add(criteriaBuilder.equal(rootUser.get("role").get("name"), filterDTO.getRole()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        List<User> users = manager.createQuery(criteriaQuery).getResultList();

        return userMapper.toDTO(users);
    }

    @Override
    @Transactional
    public void changeActive(Long id, boolean active) {
        userRepository.findById(id).ifPresentOrElse(
                user -> {
                    user.setActive(active);
                    userRepository.save(user);
                },
                () -> {
                    throw new EntityNotFoundException("User not found with ID : " + id, HttpStatus.NOT_FOUND);
                }
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
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID : " + id, HttpStatus.NOT_FOUND)));
    }

    @Override
    @Transactional
    public void create(UserDTO userDTO) {

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsWithUsernameException(userDTO.getUsername(), HttpStatus.CONFLICT);
        }

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found with ID : " + userDTO.getRoleId(), HttpStatus.NOT_FOUND)
                );

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        userRepository.save(user);

    }

    @Override
    @Transactional
    public void update(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found with ID : " + id, HttpStatus.NOT_FOUND)
        );
        if (userRepository.existsByUsername(userDTO.getUsername()) && !user.getUsername().equals(userDTO.getUsername())) {

            throw new UserAlreadyExistsWithUsernameException(userDTO.getUsername(), HttpStatus.CONFLICT);

        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found with ID : " + userDTO.getRoleId(), HttpStatus.NOT_FOUND)
                );
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID : " + id, HttpStatus.CONFLICT);
        }

        User user = optionalUser.get();

        userRepository.delete(user);

    }

} 
