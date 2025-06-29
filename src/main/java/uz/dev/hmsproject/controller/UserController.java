package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Foydalanuvchilarni filtrlash", description = "Foydalanuvchilarni filtrlash")
    @PreAuthorize(value = "hasAuthority('FILTER_USERS')")
    @GetMapping("/filter")
    public List<UserDTO> filter(@Valid UserFilterDTO filterDTO) {

        return userService.filter(filterDTO);

    }


    @Operation(summary = "Barcha foydalanuvchilarni olish", description = "Barcha foydalanuvchilarni olish")
    @PreAuthorize(value = "hasAuthority('VIEW_USERS')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return userService.getAllPaginated(page, size);

    }

    @Operation(summary = "Foydalanuvchini ID bo'yicha olish", description = "Foydalanuvchini ID bo'yicha olish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                     {
                            "id": 1,
                            "fullName": "John Doe ",
                            "username": "user1",
                            "role": "ADMIN"
                          }
                    """))
            }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "errorMs": "Forbidden", "statusCode": "403" }
                    """))
            })
    })
    @PreAuthorize(value = "hasAuthority('VIEW_USER')")

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {

        return userService.getById(id);

    }

    @Operation(summary = "Foydalanuvchini yaratish", description = "Yangi Foydalanuvchini yaratish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "message": "User created successfully" }
                    """))
            }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "errorMs": "Forbidden", "statusCode": "403" }
                    """))
            })
    })
    @PreAuthorize(value = "hasAuthority('CREATE_USERS')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO userDTO) {

        userService.create(userDTO);

        return ResponseEntity.ok("User created successfully");

    }

    @Operation(summary = "Foydalanuvchini yangilash", description = "Foydalanuvchini ID bo'yicha yangilash")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "message": "User updated successfully" }
                    """))
            }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "errorMs": "Forbidden", "statusCode": "403" }
                    """))
            })
    })
    @PreAuthorize(value = "hasAuthority('UPDATE_USERS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Foydalanuvchini o'chirish", description = "Foydalanuvchini ID bo'yicha o'chirish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "message": "User deleted successfully" }
                    """))
            }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "errorMs": "Forbidden", "statusCode": "403" }
                    """))
            })
    })
    @PreAuthorize(value = "hasAuthority('DELETE_USERS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        userService.delete(id);

        return ResponseEntity.ok("User deleted successfully");
    }

@Operation(summary = "Foydalanuvchini holatini ozgartirish", description = "Foydalanuvchini ID bo'yicha holatini ozgartirish")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = {
                @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "message": "User active status changed successfully" }
                    """))
        }),
        @ApiResponse(responseCode = "403", content = {
                @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "errorMs": "Forbidden", "statusCode": "403" }
                    """))
        })
})
    @PreAuthorize(value = "hasAuthority('UPDATE_USERS')")
    @PatchMapping("/{id}/active")
    public ResponseEntity<?> changeActive(
            @PathVariable("id") Long id,
            @RequestParam(value = "active") boolean active
    ) {
        userService.changeActive(id, active);
        return ResponseEntity.ok("User active status changed successfully");
    }

}
