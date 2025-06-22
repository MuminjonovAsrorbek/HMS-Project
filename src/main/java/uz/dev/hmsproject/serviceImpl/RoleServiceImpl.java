package uz.dev.hmsproject.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.mapper.RoleMapper;
import uz.dev.hmsproject.repository.RoleRepository;
import uz.dev.hmsproject.service.template.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDTO)
                .orElse(null);
    }

    @Override
    public void create(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        roleRepository.save(role);
    }

    @Override
    public void update(Long id, RoleDTO roleDTO) {
        if (roleRepository.existsById(id)) {
            Role role = roleMapper.toEntity(roleDTO);
            role.setId(id);
            roleRepository.save(role);
        }
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}