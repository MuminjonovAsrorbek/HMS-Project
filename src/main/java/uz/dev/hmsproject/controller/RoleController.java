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
import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.dto.response.ErrorDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.enums.Permissions;
import uz.dev.hmsproject.service.template.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Roles", description = "Endpoints for managing roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(
            summary = "Get all roles",
            description = "Retrieve a paginated list of all roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated role list returned successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            ))
    })
    @PreAuthorize("hasAuthority('VIEW_ROLES')")
    @GetMapping
    public PageableDTO getAll(
            @Parameter(description = "Page number", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(value = "size", defaultValue = "10") int size) {
        return roleService.getAllPaginated(page, size);
    }

    @Operation(summary = "Get role by ID", description = "Retrieve a specific role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role retrieved successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"ADMIN\", \"permissions\": [\"VIEW_USERS\", \"UPDATE_USERS\"] }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            )),
            @ApiResponse(responseCode = "404", description = "Entity not found exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"Role with id:5 not found.\" }")
            ))
    })
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @GetMapping("/{id}")
    public RoleDTO getById(
            @Parameter(description = "Role ID", example = "1") @PathVariable("id") Long id) {
        return roleService.getById(id);
    }

    @Operation(summary = "Create new role", description = "Create and save a new role in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role created successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"Role created successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            )),
            @ApiResponse(responseCode = "400", description = "Role Invalid Permissions Exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"Permissions cannot be null or empty.\" }")
            )),
            @ApiResponse(responseCode = "409", description = "Entity Unique Exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"Role already exists with name: ADMIN\" }")
            )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for request fields",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "code": 400,
                                              "message": "Field not valid",
                                              "fieldErrors": [
                                                {
                                                  "field": "permissions",
                                                  "message": "can not be null"
                                                },
                                                {
                                                  "field": "name",
                                                  "message": "size should be between 3 and 1000000000"
                                                }
                                              ]
                                            }
                                            """
                            )

                    )
            )

    })
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping
    public ResponseEntity<?> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = RoleDTO.class))) @RequestBody @Valid RoleDTO roleDTO) {
        roleService.create(roleDTO);
        return ResponseEntity.ok("Role created successfully");
    }

    @Operation(summary = "Update role", description = "Update an existing role by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"Role updated successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            )),
            @ApiResponse(responseCode = "404", description = "Entity not found exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"Role with id:5 not found.\" }")
            )),
            @ApiResponse(responseCode = "400", description = "Role Invalid Permissions Exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"Permissions cannot be null or empty.\" }")
            )),
            @ApiResponse(responseCode = "409", description = "Entity Unique Exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 409, \"message\": \"Role already exists with name: ADMIN\" }")
            )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed for request fields",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "code": 400,
                                              "message": "Field not valid",
                                              "fieldErrors": [
                                                {
                                                  "field": "permissions",
                                                  "message": "can not be null"
                                                },
                                                {
                                                  "field": "name",
                                                  "message": "size should be between 3 and 1000000000"
                                                }
                                              ]
                                            }
                                            """
                            )

                    )
            )
    })
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Role ID", example = "1") @PathVariable("id") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = RoleDTO.class)))
            @RequestBody @Valid RoleDTO roleDTO) {
        roleService.update(id, roleDTO);
        return ResponseEntity.ok("Role updated successfully");
    }

    @Operation(summary = "Delete role", description = "Delete a role by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role deleted successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \"Role deleted successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            )),
            @ApiResponse(responseCode = "404", description = "Entity not found exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"Role with id:5 not found.\" }")
            ))
    })
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Role ID", example = "1") @PathVariable("id") Long id) {
        roleService.delete(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

    @Operation(summary = "Get permissions", description = "Get All Role permissions.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role permissions returned successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \" Permissions retrieved successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            ))
    })
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @GetMapping("/permissions")
    public ResponseEntity<List<Permissions>> getAllPermissions() {
        return ResponseEntity.ok(roleService.getAllPermissions());
    }


    @Operation(summary = "Get permissions", description = "Get All Role permissions.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role permissions returned successfully", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"message\": \" Permissions retrieved successfully\" }")
            )),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"errorMs\": \"Forbidden\", \"statusCode\": \"403\" }")
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 500, \"message\": \"Unexpected server error occurred during processing.\" }")
            )),
            @ApiResponse(responseCode = "404", description = "Entity not found exception", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class),
                    examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"Role with id:5 not found.\" }")
            ))
    })
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @GetMapping("/permissions/{id}")
    public ResponseEntity<List<Permissions>> getAllPermissionsById(
            @Parameter(description = "Role ID", example = "1") @PathVariable("id") Long id) {
        {
            return ResponseEntity.ok(roleService.getPermissionsById(id));
        }


    }
}
