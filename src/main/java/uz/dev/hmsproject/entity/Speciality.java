package uz.dev.hmsproject.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;

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
@FieldNameConstants
public class Speciality extends AbsDeleteEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "speciality", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PriceList priceList;

}
