package com.example.taskManager.Auth;

import com.example.taskManager.Repository.RoleRepository;
import com.example.taskManager.Repository.UserRepository;
import com.example.taskManager.dto.AuthResponse;
import com.example.taskManager.exception.EmailAlreadyRegistered;
import com.example.taskManager.exception.DefaultRoleNotFoundException;
import com.example.taskManager.model.Role;
import com.example.taskManager.model.User;
import com.example.taskManager.utility.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        authService = new AuthServiceImpl(userRepository, roleRepository, passwordEncoder, jwtService);
    }

    @Test
    void register_success() throws EmailAlreadyRegistered, DefaultRoleNotFoundException {
        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pw")).thenReturn("encoded");
        when(jwtService.generateToken(any())).thenReturn("token123");

        AuthResponse resp = authService.register("First", "Last", "a@b.com", "pw");

        assertNotNull(resp);
        assertEquals("token123", resp.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicate_throws() throws DefaultRoleNotFoundException {
        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(defaultRole));

        User existing = new User();
        existing.setEmail("a@b.com");
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(existing));

        assertThrows(EmailAlreadyRegistered.class, () -> authService.register("F","L","a@b.com","p"));
    }

    @Test
    void register_defaultRoleNotFound_throws() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThrows(DefaultRoleNotFoundException.class, () -> authService.register("F","L","new@x.com","pw"));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setEmail("u@x.com");
        user.setPassword("encoded");
        when(userRepository.findByEmail("u@x.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("raw","encoded")).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("tok");

        AuthResponse r = authService.login("u@x.com","raw");
        assertEquals("tok", r.getToken());
    }

    @Test
    void login_badPassword_throws() {
        User user = new User();
        user.setEmail("u@x.com");
        user.setPassword("encoded");
        when(userRepository.findByEmail("u@x.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("raw","encoded")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.login("u@x.com","raw"));
    }

    @Test
    void login_noUser_throws() {
        when(userRepository.findByEmail("no@one.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authService.login("no@one.com","x"));
    }

    @Test
    void assignRolesByEmail_success() throws Exception {
    Role role1 = new Role(); role1.setName("ROLE_A");
    Role role2 = new Role(); role2.setName("ROLE_B");

        User user = new User(); user.setEmail("u@r.com");

        when(userRepository.findByEmail("u@r.com")).thenReturn(Optional.of(user));
    when(roleRepository.findAllById(any())).thenReturn(List.of(role1, role2));

        authService.assignRolesByEmail("u@r.com", List.of(1L,2L));

        verify(userRepository).save(any(User.class));
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void assignRolesByEmail_roleNotFound_throws() {
        User user = new User(); user.setEmail("u@r.com");
        when(userRepository.findByEmail("u@r.com")).thenReturn(Optional.of(user));
    when(roleRepository.findAllById(any())).thenReturn(List.of());

        assertThrows(com.example.taskManager.exception.RoleNotFoundException.class,
                () -> authService.assignRolesByEmail("u@r.com", List.of(99L)));
    }

    @Test
    void updateUserOwnProfile_success() {
        User user = new User();
        user.setEmail("me@p.com");
        user.setFirstName("Old"); user.setLastName("Name"); user.setPassword("old");

        when(userRepository.findByEmail("me@p.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newpw")).thenReturn("enc-newpw");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User res = authService.updateUserOwnProfile("me@p.com","newpw","New","Name");

        assertEquals("New", res.getFirstName());
        assertEquals("Name", res.getLastName());
        assertEquals("enc-newpw", res.getPassword());
    }

    @Test
    void deactivateUser_success() {
    User user = new User(); user.setActive(true);
        when(userRepository.findById(11L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        authService.changeActiveState(11L, false);

        verify(userRepository).save(any(User.class));
        assertFalse(user.isActive());
    }

    @Test
    void getUserByRoles_success() throws Exception {
        Role role = new Role(); role.setName("ROLE_ADMIN");
    User u = new User(); u.setEmail("admin@x.com"); u.setRoles(List.of(role));

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.findAllByRoles(role)).thenReturn(List.of(u));

        var res = authService.getUserByRoles("ROLE_ADMIN");
        assertEquals(1, res.size());
        assertEquals("admin@x.com", res.get(0).getEmail());
    }

    @Test
    void getUserByRoles_roleNotFound_throws() {
        when(roleRepository.findByName("NO_ROLE")).thenReturn(Optional.empty());
        assertThrows(com.example.taskManager.exception.RoleNotFoundException.class,
                () -> authService.getUserByRoles("NO_ROLE"));
    }
}
