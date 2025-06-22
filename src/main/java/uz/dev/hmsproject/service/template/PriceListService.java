package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.PriceListDto;
import java.math.BigDecimal;

public interface PriceListService extends BaseService<PriceListDto, Long> {
    void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice);
}