package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}