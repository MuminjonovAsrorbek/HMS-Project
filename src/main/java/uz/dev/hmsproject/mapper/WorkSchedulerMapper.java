package uz.dev.hmsproject.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.WorkSchedulerDTO;
import uz.dev.hmsproject.dto.response.RespWorkSchedulerDTO;
import uz.dev.hmsproject.entity.WorkScheduler;
import uz.dev.hmsproject.mapper.template.BaseMapper;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static uz.dev.hmsproject.utils.CommonUtils.getOrDef;

import java.time.DayOfWeek;

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

    public RespWorkSchedulerDTO toRespDTO(WorkScheduler workSchedulers) {
        if (workSchedulers == null) return null;

        RespWorkSchedulerDTO respWorkSchedulerDTO = new RespWorkSchedulerDTO();

        respWorkSchedulerDTO.setId(workSchedulers.getId());
        respWorkSchedulerDTO.setFullName(getOrDef(workSchedulers.getUser().getFullName(), null));
        respWorkSchedulerDTO.setRoleName(getOrDef(workSchedulers.getUser().getRole().getName(), null));

        String dayName = DayOfWeek.of(workSchedulers.getDayOfWeek())
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        respWorkSchedulerDTO.setDayOfWeek(getOrDef(dayName, null));


        respWorkSchedulerDTO.setStartTime(getOrDef(workSchedulers.getStartTime(), null));
        respWorkSchedulerDTO.setEndTime(getOrDef(workSchedulers.getEndTime(), null));
        return respWorkSchedulerDTO;
    }

    public List<RespWorkSchedulerDTO> toRespDTO(List<WorkScheduler> workSchedulers) {
        return workSchedulers.stream().map(this::toRespDTO).collect(Collectors.toList());
    }
}