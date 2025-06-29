package uz.dev.hmsproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.WorkSchedulerUpdateDto;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.entity.WorkScheduler;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.EntityUniqueException;
import uz.dev.hmsproject.mapper.WorkSchedulerMapper;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.repository.WorkSchedulerRepository;
import uz.dev.hmsproject.service.template.WorkSchedulerService;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkSchedulerServiceImpl implements WorkSchedulerService {

    private final WorkSchedulerRepository workSchedulerRepository;

    private final UserRepository userRepository;

    private final WorkSchedulerMapper mapper;

    @Override
    @Transactional
    public WorkSchedulerDTO create(WorkSchedulerDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found by id: " + dto.getUserId(), HttpStatus.NOT_FOUND));

        workSchedulerRepository.findByUserIdAndDayOfWeek(user.getId(), dto.getDayOfWeek())
                .ifPresent(ws -> {
                    throw new EntityUniqueException(
                            "Schedule already exists for this user on day: " + dto.getDayOfWeek(),
                            HttpStatus.CONFLICT);
                });

        WorkScheduler workScheduler = new WorkScheduler();
        workScheduler.setUser(user);
        workScheduler.setDayOfWeek(dto.getDayOfWeek());
        workScheduler.setStartTime(dto.getStartTime());
        workScheduler.setEndTime(dto.getEndTime());

        WorkScheduler saved = workSchedulerRepository.save(workScheduler);

        return new WorkSchedulerDTO(
                saved.getId(),
                saved.getUser().getId(),
                saved.getDayOfWeek(),
                saved.getStartTime(),
                saved.getEndTime()
        );
    }


    @Override
    @Transactional
    public void update(Long id, WorkSchedulerUpdateDto dto) {

        WorkScheduler ws = workSchedulerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work schedule not found", HttpStatus.NOT_FOUND));

        ws.setDayOfWeek(dto.getDayOfWeek());
        ws.setStartTime(dto.getStartTime());
        ws.setEndTime(dto.getEndTime());

        workSchedulerRepository.save(ws);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        WorkScheduler ws = workSchedulerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("schedule not found", HttpStatus.NOT_FOUND));
        workSchedulerRepository.delete(ws);
    }


    @Override
    public List<WorkSchedulerDTO> getByUserId(Long userId) {
        return workSchedulerRepository.findAllByUserId(userId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkSchedulerDTO getByUserIdAndDayOfWeek(Long userId, int dayOfWeek) {
        WorkScheduler ws = workSchedulerRepository
                .findByUserIdAndDayOfWeek(userId, dayOfWeek)
                .orElseThrow(() -> new EntityNotFoundException("No schedule found", HttpStatus.NOT_FOUND));
        return mapper.toDTO(ws);
    }
}