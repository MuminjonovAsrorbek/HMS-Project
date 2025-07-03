package uz.dev.hmsproject.listener;

import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.entity.AuditLog;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.repository.AuditLogRepository;
import uz.dev.hmsproject.utils.SecurityUtils;

import java.time.LocalDateTime;

@Component
public class EntityAuditListener {

    private final AuditLogRepository auditLogRepository;

    private final SecurityUtils securityUtils;

    public EntityAuditListener(@Lazy AuditLogRepository auditLogRepository, SecurityUtils securityUtils) {
        this.auditLogRepository = auditLogRepository;
        this.securityUtils = securityUtils;
    }

    @jakarta.persistence.PostPersist
    public void postPersist(Object entity) {
        saveAuditLog(entity, "CREATED");
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        saveAuditLog(entity, "UPDATED");
    }

    @PreRemove
    public void preRemove(Object entity) {
        saveAuditLog(entity, "DELETED");
    }

    private void saveAuditLog(Object entity, String action) {
        AuditLog log = new AuditLog();
        log.setUsername(securityUtils.getCurrentUser().getUsername());
        log.setEntityName(entity.getClass().getSimpleName());
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());


        if (entity instanceof AbsLongEntity baseEntity) {

            log.setEntityId(baseEntity.getId());
        }

        log.setDescription(action + " action on " + entity.getClass().getSimpleName());

        auditLogRepository.save(log);
    }
}
