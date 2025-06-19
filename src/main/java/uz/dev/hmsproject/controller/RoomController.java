package uz.dev.hmsproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.RoomDTO;
import uz.dev.hmsproject.service.template.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomDTO> getAll() {
        return roomService.getAll();

    }

    @GetMapping("/{id}")
    public RoomDTO getById(@PathVariable("id") Long id) {
        return roomService.getById(id);

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoomDTO roomDTO) {
        roomService.create(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody RoomDTO roomDTO) {
        roomService.update(id, roomDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
