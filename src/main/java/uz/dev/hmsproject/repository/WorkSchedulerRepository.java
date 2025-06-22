package uz.dev.hmsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.dev.hmsproject.entity.WorkScheduler;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSchedulerRepository extends JpaRepository<WorkScheduler, Long> {

    Optional<WorkScheduler> findByUserIdAndDayOfWeek(Long id, int dayOfWeek);
    List<WorkScheduler> findAllByUserId(Long userId);

}