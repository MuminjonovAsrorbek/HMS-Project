package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.WorkSchedulerUpdateDto;

import java.util.List;

public interface WorkSchedulerService {

    WorkSchedulerDTO create(WorkSchedulerDTO dto);

    WorkSchedulerDTO update(Long id, WorkSchedulerUpdateDto dto);

    void delete(Long id);

    List<WorkSchedulerDTO> getByUserId(Long userId);

    WorkSchedulerDTO getByUserIdAndDayOfWeek(Long userId, int dayOfWeek);
}