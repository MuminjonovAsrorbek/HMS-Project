package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;

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
@SQLDelete(sql = "update workscheduler set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class WorkScheduler extends AbsDeleteEntity {

    @ManyToOne
    private User user;

    private int dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

}