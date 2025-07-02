package uz.dev.hmsproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;

import javax.print.Doc;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 12:00
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SQLDelete(sql = "update room set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Room extends AbsDeleteEntity {

    @Column(nullable = false, unique = true)
    private String number;

    @OneToOne(mappedBy = "room")
    private Doctor doctor;


}
