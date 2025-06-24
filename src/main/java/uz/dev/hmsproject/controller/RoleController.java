package uz.dev.hmsproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.hmsproject.dto.RoleDTO;
import uz.dev.hmsproject.dto.response.PageableDTO;
import uz.dev.hmsproject.service.template.RoleService;


@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PreAuthorize(value = "hasAuthority('VIEW_ROLES')")
    @GetMapping
    public PageableDTO getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {

        return roleService.getAllPaginated(page, size);

    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping("/{id}")
    public RoleDTO getById(@PathVariable("id") Long id) {

        return roleService.getById(id);

    }

    @PreAuthorize(value = "hasAuthority('CREATE_ROLES')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RoleDTO roleDTO) {

        roleService.create(roleDTO);

        return ResponseEntity.ok("Role created successfully");
    }

    @PreAuthorize(value = "hasAuthority('UPDATE_ROLES')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody @Valid RoleDTO roleDTO) {

        roleService.update(id, roleDTO);

        return ResponseEntity.ok("Role updated successfully");
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLES')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        roleService.delete(id);

        return ResponseEntity.ok("Role deleted successfully");

    }
}
