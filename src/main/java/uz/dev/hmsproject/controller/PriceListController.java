package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Price List API", description = "Manage prices of medical specialties")
@SecurityRequirement(name = "bearerAuth")
public class PriceListController {

    private final PriceListService priceListService;

    @PreAuthorize("hasAuthority('VIEW_PRICE_LISTS')")
    @GetMapping
    @Operation(summary = "Get All Price List", description = "Retrieve all price list records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceListDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "speciality": "Cardiology",
                                                "price": 150000
                                              }
                                            ]
                                            """
                            ))
            })
    })
    public List<PriceListDTO> getAll() {
        return priceListService.getAll();
    }

    @PreAuthorize("hasAuthority('VIEW_PRICE_LIST')")
    @GetMapping("/{id}")
    @Operation(summary = "Get Price List by ID", description = "Retrieve specific price list by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceListDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "id": 1,
                                              "speciality": "Cardiology",
                                              "price": 150000
                                            }
                                            """
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Entity not found with ID: 1"))
            })
    })
    public PriceListDTO getById(@Parameter(description = "Price List ID", example = "1")
                                @PathVariable Long id) {
        return priceListService.getById(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRICE_LIST')")
    @PatchMapping("/{specialityId}/price")
    @Operation(summary = "Update Price by Speciality", description = "Update the price of a specific medical speciality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Price updated successfully"))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Speciality not found with ID:"))
            })
    })
    public ResponseEntity<?> updateSpeciality(@Parameter(description = "Speciality ID", example = "1")
                                              @PathVariable Long specialityId,
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = "New price value",
                                                      required = true,
                                                      content = @Content(
                                                              schema = @Schema(implementation = UpdatePriceDTO.class),
                                                              examples = @ExampleObject(
                                                                      value = """
                                                                              {
                                                                                "price": 185000
                                                                              }
                                                                              """
                                                              )
                                                      ))
                                              @RequestBody @Valid UpdatePriceDTO dto) {
        priceListService.updatePriceBySpecialityId(specialityId, dto.getPrice());
        return ResponseEntity.ok("Price updated successfully");
    }
}