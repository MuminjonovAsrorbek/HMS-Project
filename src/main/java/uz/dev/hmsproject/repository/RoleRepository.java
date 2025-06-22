package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.hmsproject.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}