package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 12:01
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Doctor extends AbsLongEntity {

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Speciality speciality;

    @OneToOne
    @JoinColumn(unique = true)
    private Room room;

}
