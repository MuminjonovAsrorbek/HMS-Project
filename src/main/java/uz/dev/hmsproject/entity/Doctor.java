package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

import java.util.List;

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
    private User user;

    @OneToOne
    private Speciality speciality;

    @OneToOne
    private Room room;

}
