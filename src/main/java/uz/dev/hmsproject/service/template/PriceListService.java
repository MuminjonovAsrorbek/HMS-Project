package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.PriceListDTO;

import java.math.BigDecimal;

public interface PriceListService extends BaseService<PriceListDTO, Long> {
    void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice);
}