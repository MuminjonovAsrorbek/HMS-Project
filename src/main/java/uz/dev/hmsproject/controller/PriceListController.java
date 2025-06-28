package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.dto.UpdatePriceDTO;
import uz.dev.hmsproject.service.template.PriceListService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-list")
@RequiredArgsConstructor

public class PriceListController {

    private final PriceListService priceListService;

    @PreAuthorize("hasAuthority('VIEW_PRICE_LIST')")
    @GetMapping
    public List<PriceListDTO> getAll() {

        return priceListService.getAll();

    }

    @PreAuthorize("hasAuthority('VIEW_PRICE')")
    @GetMapping("/{id}")
    public PriceListDTO getById(@PathVariable Long id) {

        return priceListService.getById(id);

    }

    @PreAuthorize("hasAuthority('UPDATE_PRICE_BY_SPECIALITY_ID')")
    @PatchMapping("/{specialityId}/price")
    public ResponseEntity<?> updateSpeciality(@PathVariable Long specialityId,
                                              @RequestBody UpdatePriceDTO dto) {

        priceListService.updatePriceBySpecialityId(specialityId, dto.getPrice());

        return ResponseEntity.ok("Price updated successfully");
    }
}