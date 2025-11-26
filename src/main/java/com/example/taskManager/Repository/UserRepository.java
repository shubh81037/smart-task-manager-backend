package com.example.taskManager.Repository;

import com.example.taskManager.model.Role;
import com.example.taskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  
{
    Optional<User> findByEmail(String email);


    List<User> findAllByRoles(Role role);
}
