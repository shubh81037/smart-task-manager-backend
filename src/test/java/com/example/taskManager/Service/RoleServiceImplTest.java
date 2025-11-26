package com.example.taskManager.Service;

import com.example.taskManager.Repository.RoleRepository;
import com.example.taskManager.exception.RoleFoundException;
import com.example.taskManager.exception.RoleNotFoundException;
import com.example.taskManager.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    private RoleRepository roleRepository;
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void createRole_success() throws RoleFoundException {
        when(roleRepository.findByName("ROLE_TEST")).thenReturn(Optional.empty());

    Role saved = mock(Role.class);
    when(saved.getId()).thenReturn(1L);
    when(saved.getName()).thenReturn("ROLE_TEST");
    when(saved.getDescription()).thenReturn("desc");

    when(roleRepository.save(any(Role.class))).thenReturn(saved);

    Role result = roleService.createRole("ROLE_TEST", "desc");

    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("ROLE_TEST", result.getName());
        verify(roleRepository).findByName("ROLE_TEST");
        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        assertEquals("desc", captor.getValue().getDescription());
    }

    @Test
    void createRole_duplicate_throws() {
    Role existing = mock(Role.class);
    when(existing.getId()).thenReturn(2L);
    when(existing.getName()).thenReturn("ROLE_TEST");

    when(roleRepository.findByName("ROLE_TEST")).thenReturn(Optional.of(existing));

        assertThrows(RoleFoundException.class, () -> roleService.createRole("ROLE_TEST", "x"));
        verify(roleRepository, never()).save(any());
    }

    @Test
    void updateRole_notFound_throws() {
        when(roleRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(5L, "name", "desc"));
    }

    @Test
    void getAllRoles_returnsList() {
    Role r1 = new Role(); r1.setName("A");
    Role r2 = new Role(); r2.setName("B");

        when(roleRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Role> all = roleService.getAllRoles();
        assertEquals(2, all.size());
        assertEquals("A", all.get(0).getName());
    }

    @Test
    void deleteRole_notFound_throws() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.deleteRole(99L));
    }

    @Test
    void deleteRole_success() throws RoleNotFoundException {

    Role r = mock(Role.class);
    when(r.getId()).thenReturn(7L);
    when(r.getName()).thenReturn("X");
    when(roleRepository.findById(7L)).thenReturn(Optional.of(r));

        roleService.deleteRole(7L);

        verify(roleRepository).deleteById(7L);
    }
}
// ctrl + alt + I - Ai agent
// ctrl + J - for terminal