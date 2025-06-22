package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 12:16
 **/


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SQLDelete(sql = "update appointment set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
@FieldNameConstants
public class Appointment extends AbsLongEntity implements AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    @JoinColumn(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @JoinColumn(nullable = false)
    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    @JoinColumn(nullable = false)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User createdBy;

}
