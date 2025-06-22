package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.PriceListDTO;
import uz.dev.hmsproject.dto.UpdatePrice;
import uz.dev.hmsproject.service.template.PriceListService;

import java.util.List;

@RestController
@RequestMapping("/api/price-list")
@RequiredArgsConstructor

public class PriceListController {

    private final PriceListService priceListService;

    @GetMapping
    public ResponseEntity<List<PriceListDTO>> getAll() {
        return ResponseEntity.ok(priceListService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceListDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(priceListService.getById(id));
    }

    @PatchMapping("/{specialityId}/price")
    public ResponseEntity<String> updateSpeciality(@PathVariable Long specialityId,
                                                   @RequestBody UpdatePrice dto) {
        priceListService.updatePriceBySpecialityId(specialityId, dto.getPrice());
        return ResponseEntity.ok("Price updated successfully");
    }
}