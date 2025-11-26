package com.example.taskManager.Service;

import com.example.taskManager.Repository.RoleRepository;
import com.example.taskManager.exception.RoleFoundException;
import com.example.taskManager.exception.RoleNotFoundException;
import com.example.taskManager.model.Role;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Role createRole(@NotBlank String name, String description) throws RoleFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(name);

        if (optionalRole.isPresent()) throw new RoleFoundException("role name should be unique");

        Role role = new Role();
        role.setDescription(description);
        role.setName(name);

        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role updateRole(@NonNull Long id, String name, String description) throws RoleNotFoundException, RoleFoundException {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("role not found of given id - " + id));

        name = name == null ? role.getName() : name ;

        role.setName(name);
        role.setDescription(description);

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(@NonNull Long id) throws RoleNotFoundException {
        roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("role not found of given id - " + id));
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
