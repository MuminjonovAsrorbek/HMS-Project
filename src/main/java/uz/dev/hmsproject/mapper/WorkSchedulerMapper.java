package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.entity.WorkScheduler;
import uz.dev.hmsproject.mapper.template.BaseMapper;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorkSchedulerMapper implements BaseMapper<WorkScheduler, WorkSchedulerDTO> {

    @Override
    public WorkScheduler toEntity(WorkSchedulerDTO dto) {
        return null;
    }

    @Override
    public WorkSchedulerDTO toDTO(WorkScheduler entity) {
        if (entity == null) return null;

        return new WorkSchedulerDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }

    @Override
    public List<WorkSchedulerDTO> toDTO(List<WorkScheduler> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}