package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.WorkSchedulerUpdateDto;
import uz.dev.hmsproject.dto.response.RespWorkSchedulerDTO;

import java.util.List;

public interface WorkSchedulerService {

    WorkSchedulerDTO create(WorkSchedulerDTO dto);

    void update(Long id, WorkSchedulerUpdateDto dto);

    void delete(Long id);

    List<RespWorkSchedulerDTO> getByUserId(Long userId);

    RespWorkSchedulerDTO getByUserIdAndDayOfWeek(Long userId, Integer dayOfWeek);

}
