package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.WorkSchedulerUpdateDto;
import uz.dev.hmsproject.dto.response.RespWorkSchedulerDTO;
import uz.dev.hmsproject.service.template.WorkSchedulerService;
import java.util.List;

@RestController
@RequestMapping("/api/work-schedulers")
@RequiredArgsConstructor
public class WorkSchedulerController {

    private final WorkSchedulerService workSchedulerService;

    @PreAuthorize("hasAuthority('VIEW_WORK_SCHEDULE')")
    @GetMapping("/user/{userId}")
    public List<RespWorkSchedulerDTO> getAllByUser(@PathVariable Long userId) {

        return workSchedulerService.getByUserId(userId);
    }

    @PreAuthorize("hasAuthority('VIEW_WORK_SCHEDULE')")
    @GetMapping("/user/{userId}/day/{dayOfWeek}")
    public RespWorkSchedulerDTO getByUserIdAndDay(@PathVariable Long userId,
                                              @PathVariable int dayOfWeek) {

        return workSchedulerService.getByUserIdAndDayOfWeek(userId, dayOfWeek);

    }

    @PreAuthorize("hasAuthority('CREATE_WORK_SCHEDULES')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid WorkSchedulerDTO dto) {

        workSchedulerService.create(dto);

        return ResponseEntity.ok("Work schedule created successfully");

    }

    @PreAuthorize("hasAuthority('UPDATE_WORK_SCHEDULES')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid WorkSchedulerUpdateDto dto) {

        workSchedulerService.update(id, dto);

        return ResponseEntity.ok("Work schedule updated successfully");

    }

    @PreAuthorize("hasAuthority('DELETE_WORK_SCHEDULES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        workSchedulerService.delete(id);

        return ResponseEntity.ok("Work schedule deleted successfully");

    }
}