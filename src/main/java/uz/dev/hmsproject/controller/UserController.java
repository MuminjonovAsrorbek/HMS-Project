package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.UserFilterDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize(value = "hasAuthority('FILTER_USERS')")
    @GetMapping("/filter")
    public List<UserDTO> filter(UserFilterDTO filterDTO) {

        return userService.filter(filterDTO);

    }

    @PreAuthorize(value = "hasAuthority('VIEW_USERS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return userService.getAllPaginated(page, size);

    }

    @PreAuthorize(value = "hasAuthority('VIEW_USER')")

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {

        return userService.getById(id);

    }

    @PreAuthorize(value = "hasAuthority('CREATE_USERS')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO userDTO) {

        userService.create(userDTO);

        return ResponseEntity.ok("User created successfully");

    }

    @PreAuthorize(value = "hasAuthority('UPDATE_USERS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid UserDTO userDTO) {

        userService.update(id, userDTO);

        return ResponseEntity.ok("User updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_USERS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        userService.delete(id);

        return ResponseEntity.ok("User deleted successfully");
    }
}
