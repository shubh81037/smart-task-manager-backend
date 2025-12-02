package com.example.taskManager.Auth;

import com.example.taskManager.dto.*;
import com.example.taskManager.exception.EmailAlreadyRegistered;
import com.example.taskManager.exception.DefaultRoleNotFoundException;
import com.example.taskManager.exception.RoleNotFoundException;
import com.example.taskManager.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Tag(name = "Authentication", description = "User registration, login, and profile management")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "Register a new user", description = "Public endpoint")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser( @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration payload",
            required = true,
            content = @Content(schema = @Schema(implementation = RegisterRequest.class))
    )
            @RequestBody RegisterRequest request) throws EmailAlreadyRegistered, DefaultRoleNotFoundException {
        //Spring will route to the global ExceptionHandler if occur
        //it means you donot need to via try-catch
        return ResponseEntity.ok(authService.register(request.getFirstName() , request.getLastName() , request.getEmail() , request.getPassword()));
    }


    @Operation(summary = "Login and receive JWT token", description = "Public endpoint")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.getEmail() , request.getPassword()));
    }
    /*
        JwtAuthenticationFilter - 1.Extract the token from the Authorization header.
                                  2.Validate the token (check signature, expiration, etc.).
                                  3.Set the authenticated user in the Security Context.
        @PreAuthorize("isAuthenticated()") -- It only checks if the Security Context has an authenticated user
    *   @AuthenticationPrincipal  -- inject Authenticated User from the Security Context (UserDetails)
     */


    @Operation(summary = "Update own profile", description = "Requires authentication")
    @SecurityRequirement(name = "bearerAuth")       //for swagger to know what methods needs Jwt
    @PutMapping
    @PreAuthorize( "isAuthenticated()" )
    public ResponseEntity<UpdatedUserResponse> updateUserOwnProfile(@AuthenticationPrincipal UserDetails userDetails ,@RequestParam(required = false) String firstName , @RequestParam(required = false) String lastName , @RequestParam(required = false) String password  )
    {
        User user = authService.updateUserOwnProfile(userDetails.getUsername() , password , firstName , lastName);
        return ResponseEntity.ok(new UpdatedUserResponse(user.getFirstName(),user.getLastName()));
    }

    /*
    * @PreAuthorize( "hasRole('ADMIN')" ) -- provided by spring security - allows method level access control, runs before method is executed
    *                                       --to enable @PreAuthorize , annoted @EnableMethodSecurity above SecurityConfig class
    */


    @Operation(summary = "Assign roles to a user by email", description = "Admin-only")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/admin")
    @PreAuthorize( "hasRole('ADMIN')" )
    public ResponseEntity<?> assignRolesByEmail(@RequestBody RoleAssignmentDto requestDto) throws RoleNotFoundException {
        authService.assignRolesByEmail(requestDto.getEmail() , requestDto.getRoleIds());
        return ResponseEntity.ok("Roles updated");
    }


    @Operation(summary = "Change user active state", description = "Admin-only")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/admin/activeState/{id}")       // needs to be tested and change the login accordingly
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeActiveState(@Parameter(description = "User ID") @PathVariable Long id , @Parameter(description = "New active state") @RequestParam( name = "active") boolean state)
    {
        authService.changeActiveState(id , state) ;

        return ResponseEntity.ok("active state of user changed, set - "+state) ;
    }



    @Operation(summary = "Get all users", description = "Admin-only")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUser()
    {
        return ResponseEntity.ok(authService.getAllUser()) ;
    }



    @Operation(summary = "Get users by role", description = "Admin-only")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/admin/userByRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getUserByRoles(@Parameter(description = "Role name") @RequestParam String roleName) throws RoleNotFoundException {
        return ResponseEntity.ok(authService.getUserByRoles(roleName) ) ;
    }
}

// Test completed of all the functions

