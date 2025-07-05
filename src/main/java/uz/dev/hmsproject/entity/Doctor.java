package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;

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
@SQLDelete(sql = "update doctor set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
@FieldNameConstants
public class Doctor extends AbsDeleteEntity {

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    private Speciality speciality;

    @OneToOne
    private Room room;

}
