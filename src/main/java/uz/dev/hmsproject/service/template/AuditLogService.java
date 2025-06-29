package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.AuditLogDTO;
import java.util.List;

public interface AuditLogService {
    List<AuditLogDTO> getAllLogs();
    List<AuditLogDTO> getLogsByUsername(String username);
}