package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.entity.WorkScheduler;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.EntityUniqueException;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.repository.WorkSchedulerRepository;
import uz.dev.hmsproject.service.template.WorkSchedulerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkSchedulerServiceImpl implements WorkSchedulerService {

    private final WorkSchedulerRepository workSchedulerRepository;
    private final UserRepository userRepository;

    @Override
    public WorkSchedulerDTO create(WorkSchedulerDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found by id" + dto.getUserId(), HttpStatus.NOT_FOUND));

        Optional<WorkScheduler> existingSchedule = workSchedulerRepository
                .findByUserIdAndDayOfWeek(user.getId(), dto.getDayOfWeek());

        if (existingSchedule.isPresent()) {
            throw new EntityUniqueException("Schedule already exists for this user on this day", HttpStatus.CONFLICT);
        }

        WorkScheduler ws = new WorkScheduler();
        ws.setUser(user);
        ws.setDayOfWeek(dto.getDayOfWeek());
        ws.setStartTime(dto.getStartTime());
        ws.setEndTime(dto.getEndTime());

        WorkScheduler saved = workSchedulerRepository.save(ws);
        dto.setId(saved.getId());

        return dto;
    }

    @Override
    public WorkSchedulerDTO update(Long id, WorkSchedulerDTO dto) {
        WorkScheduler ws = workSchedulerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work schedule not found", HttpStatus.NOT_FOUND));

        if (!ws.getUser().getId().equals(dto.getUserId())) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found", HttpStatus.NOT_FOUND));
            ws.setUser(user);
        }

        ws.setDayOfWeek(dto.getDayOfWeek());
        ws.setStartTime(dto.getStartTime());
        ws.setEndTime(dto.getEndTime());

        WorkScheduler saved = workSchedulerRepository.save(ws);
        return mapToDto(saved);
    }

    @Override
    public void delete(Long id) {
        WorkScheduler ws = workSchedulerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("schedule not found", HttpStatus.NOT_FOUND));
        workSchedulerRepository.delete(ws);
    }


    @Override
    public List<WorkSchedulerDTO> getByUserId(Long userId) {
        return workSchedulerRepository.findAllByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkSchedulerDTO getByUserIdAndDayOfWeek(Long userId, int dayOfWeek) {
        WorkScheduler ws = workSchedulerRepository
                .findByUserIdAndDayOfWeek(userId, dayOfWeek)
                .orElseThrow(() -> new EntityNotFoundException("No schedule found", HttpStatus.NOT_FOUND));
        return mapToDto(ws);
    }

    private WorkSchedulerDTO mapToDto(WorkScheduler ws) {
        return new WorkSchedulerDTO(
                ws.getId(),
                ws.getUser().getId(),
                ws.getDayOfWeek(),
                ws.getStartTime(),
                ws.getEndTime()
        );
    }
}
