package uz.dev.hmsproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.UserDTO;
import uz.dev.hmsproject.dto.UserFilterDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.dto.response.RespUserDTO;
import uz.dev.hmsproject.service.template.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Filter users", description = "Filter users by full name, username, and role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users filtered successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('FILTER_USERS')")
    @GetMapping("/filter")
    public List<RespUserDTO> filter(
            @Parameter(description = "Filter criteria object") @Valid UserFilterDTO filterDTO) {
        return userService.filter(filterDTO);
    }

    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated list returned successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('VIEW_USERS')")
    @GetMapping
    public PageableDTO getAll(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {
        return userService.getAllPaginated(page, size);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user information by user ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 1, \"fullName\": \"John Doe\", \"username\": \"user1\", \"role\": \"ADMIN\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('VIEW_USER')")
    @GetMapping("/{id}")
    public RespUserDTO getById(
            @Parameter(description = "User ID", example = "2") @PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Create new user", description = "Create and save a new user in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"User created successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "User data (JSON)") @RequestBody @Valid UserDTO userDTO) {
        userService.create(userDTO);
        return ResponseEntity.ok("User created successfully");
    }

    @Operation(summary = "Update user", description = "Update user data by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"User updated successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "User ID", example = "2") @PathVariable("id") Long id,
            @Parameter(description = "Updated user data (JSON)") @RequestBody @Valid UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

    @Operation(summary = "Delete user", description = "Delete user by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"User deleted successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('DELETE_USERS')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "User ID", example = "2") @PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Change user active status", description = "Enable or disable user by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User active status changed successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"User active status changed successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            ))
    })
    @PreAuthorize("hasAuthority('UPDATE_USERS')")
    @PatchMapping("/{id}/active")
    public ResponseEntity<?> changeActive(
            @Parameter(description = "User ID", example = "2") @PathVariable("id") Long id,
            @Parameter(description = "New active state (true/false)", example = "false") @RequestParam(value = "active") boolean active) {
        userService.changeActive(id, active);
        return ResponseEntity.ok("User active status changed successfully");
    }
}
