package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.SpecialityCreationDTO;
import uz.dev.hmsproject.dto.SpecialityDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.SpecialityService;

@RestController
@RequestMapping("/api/speciality")
@RequiredArgsConstructor
@Tag(name = "Speciality API", description = "Manage specialities of hospital")
public class SpecialityController {

    private final SpecialityService specialityService;

    @Operation(summary = "Get all specialities",
            description = "Retrieve a paginated list of all specialities.")
    @PreAuthorize(value = "hasAuthority('VIEW_SPECIALITIES')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return specialityService.getAllByPage(page, size);

    }

    @Operation(summary = "Get speciality by ID",
            description = "Retrieve a specific speciality by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Speciality retrieved successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = SpecialityDTO.class),
                            examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Kardiolog\" }"))
            }),
            @ApiResponse(responseCode = "404", description = "Speciality not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id")))
    })
    @PreAuthorize(value = "hasAuthority('VIEW_SPECIALITY')")
    @GetMapping("/{id}")
    public SpecialityDTO getById(@PathVariable("id") Long id) {

        return specialityService.getById(id);

    }

    @Operation(summary = "Create speciality",
            description = "Create and save a new speciality in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Speciality created successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "The speciality already exist with name"
                            ))
            })
    })
    @PreAuthorize(value = "hasAuthority('CREATE_SPECIALITY')")
    @PostMapping
    public ResponseEntity<?> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create speciality",
            required = true, content = @Content(schema = @Schema(implementation = SpecialityCreationDTO.class), mediaType = "application/json")) @RequestBody @Valid SpecialityCreationDTO specialityDTO) {

        specialityService.create(specialityDTO);

        return ResponseEntity.ok("Speciality created successfully");

    }

    @Operation(summary = "Update speciality",
            description = "Update an existing speciality in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Speciality updated successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "The speciality already exist with name"
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id:"))
            })

    })
    @PreAuthorize(value = "hasAuthority('UPDATE_SPECIALITY')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update speciality", required = true,
                                            content = @Content(schema = @Schema(implementation = SpecialityDTO.class), mediaType = "application/json"))
                                    @RequestBody @Valid SpecialityDTO specialityDTO) {

        specialityService.update(id, specialityDTO);

        return ResponseEntity.ok("Speciality updated successfully");

    }

    @Operation(summary = "Delete speciality",
            description = "Delete an existing speciality in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples =
                    @ExampleObject(value = "Speciality deleted successfully"))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with id:"))
            })
    })
    @PreAuthorize(value = "hasAuthority('DELETE_SPECIALITY')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        specialityService.delete(id);

        return ResponseEntity.ok("Speciality deleted successfully");

    }
}
