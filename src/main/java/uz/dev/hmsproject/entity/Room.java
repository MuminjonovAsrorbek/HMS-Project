package uz.dev.hmsproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

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
public class Room extends AbsLongEntity {

    @Column(nullable = false, unique = true)
    private String number;

}
