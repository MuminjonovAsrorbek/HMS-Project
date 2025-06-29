package uz.dev.hmsproject.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditLogDTO {

    private Long id;
    private String username;
    private String entityName;
    private Long entityId;
    private String action;
    private String description;
    private LocalDateTime timestamp;
}