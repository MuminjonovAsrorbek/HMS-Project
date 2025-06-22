package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.PriceListDto;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.mapper.template.BaseMapper;

@Component
public class PriceListMapper implements BaseMapper<PriceList, PriceListDto> {

    @Override
    public PriceListDto toDTO(PriceList priceList) {
        PriceListDto dto = new PriceListDto();
        dto.setId(priceList.getId());
        dto.setSpecialityId(priceList.getSpeciality().getId());
        dto.setPrice(priceList.getPrice());
        dto.setUpdateAt(priceList.getUpdateAt());
        return dto;
    }

    @Override
    public PriceList toEntity(PriceListDto priceListDto) {
        return null;
    }
}