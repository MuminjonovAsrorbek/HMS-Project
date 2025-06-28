package uz.dev.hmsproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.entity.PriceList;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 14:44
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceListMapper {

    @Mapping(target = "specialityId", source = "speciality.id")
    PriceListDTO toDTO(PriceList priceList);

    List<PriceListDTO> toDTO(List<PriceList> priceLists);

}
