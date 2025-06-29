package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.exception.EntityNotDeleteException;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.EntityUniqueException;
import uz.dev.hmsproject.exception.RoleInvalidPermissionsException;
import uz.dev.hmsproject.mapper.RoleMapper;
import uz.dev.hmsproject.repository.RoleRepository;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.service.template.RoleService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final UserRepository userRepository;

    @Override
    public PageableDTO getAllPaginated(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Role> rolesPage = roleRepository.findAll(pageable);

        List<Role> roles = rolesPage.getContent();

        List<RoleDTO> roleDTOS = roleMapper.toDTO(roles);

        return new PageableDTO(
                rolesPage.getSize(),
                rolesPage.getTotalElements(),
                rolesPage.getTotalPages(),
                !rolesPage.isLast(),
                !rolesPage.isFirst(),
                roleDTOS
        );
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleMapper.toDTO(roleRepository.findAll());
    }

    @Override
    public RoleDTO getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id, HttpStatus.NOT_FOUND));
        return roleMapper.toDTO(role);
    }

    @Override
    @Transactional
    public void create(RoleDTO roleDTO) {

        if (roleRepository.existsByName(roleDTO.getName())) {
            throw new EntityUniqueException("Role already exists with name: " + roleDTO.getName(), HttpStatus.CONFLICT);
        }

        if (roleDTO.getPermissions() == null || roleDTO.getPermissions().isEmpty()) {
            throw new RoleInvalidPermissionsException("Permissions cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Role role = new Role();

        role.setName(roleDTO.getName());
        role.setPermissions(roleDTO.getPermissions());

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void update(Long id, RoleDTO roleDTO) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id, HttpStatus.NOT_FOUND));

        if (roleRepository.existsByName(roleDTO.getName()) && !role.getName().equals(roleDTO.getName())) {
            throw new EntityUniqueException("Role already exists with name: " + roleDTO.getName(), HttpStatus.CONFLICT);
        }

        if (roleDTO.getPermissions() == null || roleDTO.getPermissions().isEmpty()) {
            throw new RoleInvalidPermissionsException("Permissions cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        role.setName(roleDTO.getName());

        role.setPermissions(roleDTO.getPermissions());

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty()) {

            throw new EntityNotFoundException("Role not found with id: " + id, HttpStatus.NOT_FOUND);

        }

        Role role = optionalRole.get();

        boolean exist = userRepository.existsByRoleId(role.getId());

        if (exist)
            throw new EntityNotDeleteException("Role not deleted , You first remove role in user", HttpStatus.BAD_REQUEST);

        roleRepository.delete(role);
    }
}
