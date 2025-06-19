package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
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
public class Appointment extends AbsLongEntity {

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private LocalDateTime dateTime;

    private Integer roomId;

    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    private User createdBy;

}
