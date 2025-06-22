package uz.dev.hmsproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PriceList extends AbsLongEntity {

    @OneToOne
    @JoinColumn(unique = true)
    private Speciality speciality;

    private BigDecimal price;

    @UpdateTimestamp
    private Timestamp updateAt;

}