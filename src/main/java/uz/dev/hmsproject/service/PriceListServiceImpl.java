package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.entity.PriceList;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.mapper.PriceListMapper;
import uz.dev.hmsproject.repository.PriceListRepository;
import uz.dev.hmsproject.service.template.PriceListService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PriceListServiceImpl implements PriceListService {

    private final PriceListRepository priceListRepository;
    private final PriceListMapper priceListMapper;

    @Override
    public List<PriceListDTO> getAll() {

        return priceListRepository.findAll().stream()
                .map(priceListMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PriceListDTO getById(Long id) {

        PriceList priceList = priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PriceList not found with id: " + id, HttpStatus.NOT_FOUND));
        return priceListMapper.toDTO(priceList);
    }

    @Override
    public void updatePriceBySpecialityId(Long specialityId, BigDecimal newPrice) {
        PriceList priceList = priceListRepository.findBySpecialityId(specialityId)
                .orElseThrow(() -> new EntityNotFoundException("PriceList not found for specialityId: " + specialityId, HttpStatus.NOT_FOUND));
        priceList.setPrice(newPrice);
        priceListRepository.save(priceList);
    }

}