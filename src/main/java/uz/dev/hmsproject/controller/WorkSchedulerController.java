package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.entity.WorkScheduler;
import uz.dev.hmsproject.mapper.WorkSchedulerMapper;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.repository.WorkSchedulerRepository;
import uz.dev.hmsproject.service.template.WorkSchedulerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/work-schedulers")
@RequiredArgsConstructor
public class WorkSchedulerController {

    private final WorkSchedulerRepository workSchedulerRepository;
    private final UserRepository userRepository;
    private final WorkSchedulerMapper mapper;

    @PostMapping
    public ResponseEntity<WorkSchedulerDTO> create(@RequestBody WorkSchedulerDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean exists = workSchedulerRepository
                .findByUserIdAndDayOfWeek(dto.getUserId(), dto.getDayOfWeek())
                .isPresent();
        if (exists) {
            return ResponseEntity.status(409).build();
        }

        WorkScheduler entity = new WorkScheduler();
        entity.setUser(optionalUser.get());
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());

        WorkScheduler saved = workSchedulerRepository.save(entity);
        return ResponseEntity.ok(mapper.toDTO(saved));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkSchedulerDTO>> getAllByUser(@PathVariable Long userId) {
        List<WorkScheduler> list = workSchedulerRepository.findAllByUserId(userId);
        return ResponseEntity.ok(mapper.toDTO(list));
    }


    @PutMapping("/{id}")
    public ResponseEntity<WorkSchedulerDTO> update(@PathVariable Long id, @RequestBody WorkSchedulerDTO dto) {
        Optional<WorkScheduler> optionalWS = workSchedulerRepository.findById(id);
        if (optionalWS.isEmpty()) return ResponseEntity.notFound().build();

        Optional<User> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()) return ResponseEntity.badRequest().build();

        WorkScheduler entity = optionalWS.get();
        entity.setUser(user.get());
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());

        WorkScheduler saved = workSchedulerRepository.save(entity);
        return ResponseEntity.ok(mapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!workSchedulerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        workSchedulerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/day/{dayOfWeek}")
    public ResponseEntity<WorkSchedulerDTO> getByUserIdAndDay(@PathVariable Long userId,
                                                              @PathVariable int dayOfWeek) {
        Optional<WorkScheduler> optional = workSchedulerRepository
                .findByUserIdAndDayOfWeek(userId, dayOfWeek);
        return optional.map(workScheduler -> ResponseEntity.ok(mapper.toDTO(workScheduler)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
