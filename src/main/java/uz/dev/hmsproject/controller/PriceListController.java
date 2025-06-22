package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.dto.UpdatePrice;
import uz.dev.hmsproject.service.template.PriceListService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-list")
@RequiredArgsConstructor

public class PriceListController {

    private final PriceListService priceListService;

    @PreAuthorize("hasAuthority('VIEW_PRICE_LIST')")
    @GetMapping
    public ResponseEntity<List<PriceListDTO>> getAll() {
        return ResponseEntity.ok(priceListService.getAll());
    }

    @PreAuthorize("hasAuthority('VIEW_PRICE')")
    @GetMapping("/{id}")
    public ResponseEntity<PriceListDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(priceListService.getById(id));
    }

    @PreAuthorize("hasAuthority('UPDATE_PRICE_BY_SPECIALITY_ID')")
    @PatchMapping("/{specialityId}/price")
    public ResponseEntity<String> updateSpeciality(@PathVariable Long specialityId,
                                                   @RequestBody UpdatePrice dto) {
        priceListService.updatePriceBySpecialityId(specialityId, dto.getPrice());
        return ResponseEntity.ok("Price updated successfully");
    }
}