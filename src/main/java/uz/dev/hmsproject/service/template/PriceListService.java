package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.PriceListDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PriceListService {
    void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice);

    List<PriceListDTO> getAll();

    PriceListDTO getById(Long id);
}