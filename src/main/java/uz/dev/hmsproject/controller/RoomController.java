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
import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.RoomService;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
@Tag(name = "Room API", description = "Manage rooms of hospital")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Get all rooms",
            description = "Retrieve a paginated list of all rooms.")
    @PreAuthorize(value = "hasAuthority('VIEW_ROOMS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return roomService.getAll(page, size);

    }

    @Operation(summary = "Get Room by ID",
            description = "Retrieve a specific room by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room retrieved successfully", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = RoomDTO.class),
                    examples = @ExampleObject(value = "{ \"id\": 1, \"number\": \"Room 1\" }")
            )),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(
                    value = "Entity not found with ID"
            )))
    })
    @PreAuthorize(value = "hasAuthority('VIEW_ROOM')")
    @GetMapping("/{id}")
    public RoomDTO getById(@PathVariable("id") Long id) {
        return roomService.getById(id);

    }

    @PreAuthorize(value = "hasAuthority('CREATE_ROOM')")
    @Operation(summary = "Create Room",
            description = "Create and save a new room in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Room create successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "The room already exist with number"
                            ))})
    })
    @PreAuthorize(value = "hasAuthority('CREATE_ROOMS')")
    @PostMapping
    public ResponseEntity<?> create(@io.swagger.v3.oas.annotations                                      .parameters.RequestBody(description =                                   "Create room", required = true, content =
                                    @Content(schema = @Schema(implementation = RoomDTO.class), mediaType = "application/json")) @RequestBody @Valid RoomDTO roomDTO
    ) {

        roomService.create(roomDTO);

        return ResponseEntity.ok("Room created successfully");
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_ROOM')")
    @Operation(summary = "Update Room",
            description = "Update an existing room in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "Room update successfully"
                            ))
            }),
            @ApiResponse(responseCode = "209", content = {
                    @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "The room already exist with number"
                            ))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with ID:"))
            })
    })
    @PreAuthorize(value = "hasAuthority('UPDATE_ROOMS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Update room",required = true,
                                    content = @Content(schema = @Schema(implementation = RoomDTO.class), mediaType = "application/json"))
                                    @RequestBody @Valid RoomDTO roomDTO) {

        roomService.update(id, roomDTO);

        return ResponseEntity.ok("Room updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROOM')")
    @Operation(summary = "Delete room",
    description = "Delete an existing room in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples =
                    @ExampleObject(value = "Room deleted successfully"))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = "Entity not found with ID:"))
            })
    })
    @PreAuthorize(value = "hasAuthority('DELETE_ROOMS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        roomService.delete(id);

        return ResponseEntity.ok("Room deleted successfully");
    }
}
