package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.hmsproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}