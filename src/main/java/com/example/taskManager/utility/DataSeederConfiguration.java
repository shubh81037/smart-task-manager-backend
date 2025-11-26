package com.example.taskManager.utility;

import com.example.taskManager.Repository.RoleRepository;
import com.example.taskManager.Repository.UserRepository;
import com.example.taskManager.model.Role;
import com.example.taskManager.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//For testing purpose
@Configuration
public class DataSeederConfiguration
{
    private RoleRepository roleRepository ;
    private UserRepository userRepository ;
    private PasswordEncoder passwordEncoder ;


    public DataSeederConfiguration(RoleRepository roleRepository, UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder ;
    }

    @Bean
    public CommandLineRunner seedDatabase()
    {
        return args -> seedInitialTestingData();
    }

    @Transactional
    public void seedInitialTestingData()
    {

        // Ensure ROLE_ADMIN exists and get the managed instance
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);

        if(userRepository.findByEmail("admin@example.com").isEmpty()){

            User admin = new User();
            admin.setActive(true);
            admin.setEmail("admin@example.com");
            admin.setFirstName("System ");
            admin.setLastName("Admin");
            admin.setPassword(passwordEncoder.encode("admin123"));

            if (adminRole == null)
            {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                adminRole.setDescription("admin role for admin");

            }

            admin.setRoles(List.of(adminRole));

            userRepository.save(admin) ;
        }

    }
}
