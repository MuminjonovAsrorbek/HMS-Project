package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.hmsproject.dto.PriceListDto;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.mapper.PriceListMapper;
import uz.dev.hmsproject.repository.PriceListRepository;
import uz.dev.hmsproject.service.template.PriceListService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PriceServiceImpl implements PriceListService {

    private final PriceListRepository priceListRepository;
    private final PriceListMapper priceListMapper;

    @Override
    public List<PriceListDto> getAll() {
        return priceListRepository.findAll().stream()
                .map(priceListMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PriceListDto getById(Long id) {
        PriceList priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceList not found"));
        return priceListMapper.toDTO(priceList);
    }

    @Override
    public void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice) {
        PriceList priceList = priceListRepository.findBySpecialityId(specialityId)
                .orElseThrow(() -> new RuntimeException("PriceList not found for specialityId: " + specialityId));
        priceList.setPrice(newPrice);
        priceListRepository.save(priceList);
    }

    @Override
    public void create(PriceListDto dto) {
    }

    @Override
    public void update(Long aLong, PriceListDto dto) {
    }

    @Override
    public void delete(Long aLong) {
    }
}