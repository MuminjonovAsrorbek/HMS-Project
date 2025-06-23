package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.hmsproject.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
