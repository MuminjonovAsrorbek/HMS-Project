package uz.dev.hmsproject.controller;

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
public class RoomController {

    private final RoomService roomService;

    @PreAuthorize(value = "hasAuthority('VIEW_ROOMS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return roomService.getAll(page, size);

    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROOM')")
    @GetMapping("/{id}")
    public RoomDTO getById(@PathVariable("id") Long id) {
        return roomService.getById(id);

    }

    @PreAuthorize(value = "hasAuthority('CREATE_ROOM')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RoomDTO roomDTO) {

        roomService.create(roomDTO);

        return ResponseEntity.ok("Room created successfully");
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_ROOM')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid RoomDTO roomDTO) {

        roomService.update(id, roomDTO);

        return ResponseEntity.ok("Room updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROOM')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        roomService.delete(id);

        return ResponseEntity.ok("Room deleted successfully");
    }
}
