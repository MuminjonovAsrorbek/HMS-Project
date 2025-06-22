package uz.dev.hmsproject.mapper;

import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.mapper.template.BaseMapper;

import java.util.List;

@Component
public class PriceListMapper implements BaseMapper<PriceList, PriceListDTO> {

    @Override
    public PriceListDTO toDTO(PriceList priceList) {
        PriceListDTO dto = new PriceListDTO();
        dto.setId(priceList.getId());
        dto.setSpecialityId(priceList.getSpeciality().getId());
        dto.setPrice(priceList.getPrice());
        dto.setUpdateAt(priceList.getUpdateAt());
        return dto;
    }

    @Override
    public List<PriceListDTO> toDTO(List<PriceList> dtos) {
        return List.of();
    }

    @Override
    public PriceList toEntity(PriceListDTO priceListDto) {
        return null;
    }
}