package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return userService.getAllPaginated(page, size);

    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {

        return userService.getById(id);

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO userDTO) {

        userService.create(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid UserDTO userDTO) {
        userService.update(id, userDTO);

        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        userService.delete(id);

        // to - do => databasedan ma'lumot o'chmasligi kerak .

        return ResponseEntity.noContent().build();
    }
}
