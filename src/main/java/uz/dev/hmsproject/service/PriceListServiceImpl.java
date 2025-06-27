package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.mapper.PriceListMapper;
import uz.dev.hmsproject.repository.PriceListRepository;
import uz.dev.hmsproject.service.template.PriceListService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListServiceImpl implements PriceListService {

    private final PriceListRepository priceListRepository;

    private final PriceListMapper priceListMapper;

    @Override
    public List<PriceListDTO> getAll() {

        List<PriceList> priceLists = priceListRepository.findAll();

        return priceListMapper.toDTO(priceLists);

    }

    @Override
    public PriceListDTO getById(Long id) {

        PriceList priceList = priceListRepository.findByIdOrThrow(id);

        return priceListMapper.toDTO(priceList);

    }

    @Override
    @Transactional
    public void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice) {

        PriceList priceList = priceListRepository.findBySpecialityIdOrThrow(specialityId);

        priceList.setPrice(newPrice);

        priceListRepository.save(priceList);

    }

}