package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 11:59
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@SQLDelete(sql = "update speciality set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Speciality extends AbsLongEntity implements AbsDeleteEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "speciality", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PriceList priceList;

}
