package uz.dev.hmsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.hmsproject.entity.PriceList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DTO for {@link PriceList}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListDto implements Serializable {

    private Long id;

    private Long specialityId;

    private BigDecimal price;

    private Timestamp updateAt;
}