package com.example.taskManager.Controller;

import com.example.taskManager.Service.RoleService;
import com.example.taskManager.dto.CreateRoleDTO;
import com.example.taskManager.exception.RoleFoundException;
import com.example.taskManager.model.Role;
// constructor injection is used; @Autowired is not required
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.taskManager.exception.RoleNotFoundException;
import org.springframework.lang.NonNull;
import java.util.List;

/*
@Tag , @SecurityRequirement , @Operation , @Parameter  --- all are for swagger
*
*  */

@Tag(name = "Roles", description = "Admin-only role management")
@SecurityRequirement(name = "bearerAuth")       // helps swagger to know what methods needs JWT token , here all the methods needs that's why i annotate at class level
@RestController
@RequestMapping("/api/roles")
public class RoleController
{
    private final RoleService roleService ;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Create a new role", description = "Only accessible by ADMIN users")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole( @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Role name and description",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateRoleDTO.class))
    )
            @RequestBody CreateRoleDTO request) throws RoleFoundException {
        Role role = roleService.createRole(request.getName(), request.getDescription()) ;
        return ResponseEntity.ok(role) ;
    }


    @Operation(summary = "Update an existing role", description = "Only accessible by ADMIN users")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@Parameter(description = "Role ID")  @PathVariable @NonNull Long id , @RequestParam(required = false) String roleName , @RequestParam(required = false) String description  ) throws RoleNotFoundException, RoleFoundException {
        Role role = roleService.updateRole(id ,roleName, description) ;
        return ResponseEntity.ok(role) ;
    }

    @Operation(summary = "Get all roles", description = "Only accessible by ADMIN users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRole()
    {
        List<Role> roles  = roleService.getAllRoles() ;
        return ResponseEntity.ok(roles) ;
    }

    @Operation(summary = "Delete a role by ID", description = "Only accessible by ADMIN users")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@Parameter(description = "Role ID")   @PathVariable @NonNull Long id ) throws RoleNotFoundException {
        roleService.deleteRole(id) ;
        return ResponseEntity.noContent().build() ;
    }
}

// Test completed of all the functions

