package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.AuditLogDTO;
import uz.dev.hmsproject.mapper.AuditLogMapper;
import uz.dev.hmsproject.repository.AuditLogRepository;
import uz.dev.hmsproject.service.template.AuditLogService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    public List<AuditLogDTO> getAllLogs() {
        return auditLogMapper.toDTO(auditLogRepository.findAll());
    }

    @Override
    public List<AuditLogDTO> getLogsByUsername(String username) {
        return auditLogMapper.toDTO(auditLogRepository
                .findAll()
                .stream()
                .filter(log -> log.getUsername().equalsIgnoreCase(username))
                .toList());
    }
}