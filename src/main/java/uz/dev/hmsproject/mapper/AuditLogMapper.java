package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.AuditLogDTO;
import uz.dev.hmsproject.entity.AuditLog;
import uz.dev.hmsproject.mapper.template.BaseMapper;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuditLogMapper implements BaseMapper<AuditLog, AuditLogDTO> {

    @Override
    public AuditLog toEntity(AuditLogDTO dto) {
        if (dto == null) return null;

        AuditLog log = new AuditLog();
        log.setId(dto.getId());
        log.setUsername(dto.getUsername());
        log.setEntityName(dto.getEntityName());
        log.setEntityId(dto.getEntityId());
        log.setAction(dto.getAction());
        log.setDescription(dto.getDescription());
        log.setTimestamp(dto.getTimestamp());
        return log;
    }

    @Override
    public AuditLogDTO toDTO(AuditLog entity) {
        if (entity == null) return null;

        return new AuditLogDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEntityName(),
                entity.getEntityId(),
                entity.getAction(),
                entity.getDescription(),
                entity.getTimestamp()
        );
    }

    @Override
    public List<AuditLogDTO> toDTO(List<AuditLog> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}