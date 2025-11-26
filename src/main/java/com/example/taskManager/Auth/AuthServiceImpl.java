package com.example.taskManager.Auth;

import com.example.taskManager.Repository.RoleRepository;
import com.example.taskManager.Repository.UserRepository;
import com.example.taskManager.dto.*;
import com.example.taskManager.exception.EmailAlreadyRegistered;
import com.example.taskManager.exception.DefaultRoleNotFoundException;
import com.example.taskManager.model.Role;
import com.example.taskManager.model.User;
import com.example.taskManager.utility.CustomUserDetails;
import com.example.taskManager.utility.JwtService;
import com.example.taskManager.exception.RoleNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public AuthResponse register(String firstName, String lastName, String email, String password) throws EmailAlreadyRegistered, DefaultRoleNotFoundException {
        Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new DefaultRoleNotFoundException("default roles are not found"));
        User user = new User();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegistered("email already registered");
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(List.of(defaultRole));
        userRepository.save(user);

        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.isActive())
        {
            throw new AccessDeniedException("your access has been revoked") ;
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    @Transactional
    @Override
    public User updateUserOwnProfile(String username, String password, String firstName, String lastName) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("email is not register"));

        firstName = firstName == null ? user.getFirstName() : firstName ;
        lastName = lastName == null ? user.getLastName() : lastName ;

        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (password != null)
        {
            user.setPassword(passwordEncoder.encode(password));
        }

        return userRepository.save(user);

    }

    @Transactional
    @Override
    public void changeActiveState(Long id, boolean activeStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(activeStatus);

        userRepository.save(user);

    }

    @Override
    public List<UserResponseDto> getAllUser() {
        return userRepository.findAll().stream().map(this::ConvertUserToUserResponseDto).toList();
    }

    private UserResponseDto ConvertUserToUserResponseDto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setActiveStatus(user.isActive());
        responseDto.setRoles(user.getRoles().stream().map(Role::getName).toList());

        return responseDto;
    }

    @Transactional
    @Override
    public void assignRolesByEmail(String email, List<Long> roleIds) throws RoleNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not found "));

        List<Role> rolesList = roleRepository.findAllById(roleIds);

        if (rolesList.size() != roleIds.size()) {
            throw new RoleNotFoundException("some roles are not found");
        }

        user.setRoles(rolesList);

        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getUserByRoles(String roleName) throws RoleNotFoundException {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("role not found"));

        List<User> userList = userRepository.findAllByRoles(role);

        return userList.stream().map(this::ConvertUserToUserResponseDto).toList();
    }
}
