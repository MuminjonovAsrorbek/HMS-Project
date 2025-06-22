package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private LocalDateTime dateTime;

    @ManyToOne
    private Room room;

    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    private User createdBy;

}
