package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

import java.time.LocalTime;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 12:03
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class WorkScheduler extends AbsLongEntity {

    @ManyToOne
    private User user;

    private int dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

}
