package com.example.taskManager.Service;

import com.example.taskManager.Repository.TaskRepository;
import com.example.taskManager.Repository.UserRepository;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        userRepository = mock(UserRepository.class);
        taskService = new TaskServiceImpl(taskRepository, userRepository);
    }

    @Test
    void createTask_success() {
        User user = new User();
        user.setEmail("u@example.com");
        when(userRepository.findByEmail("u@example.com")).thenReturn(Optional.of(user));

        Task saved = new Task();
        saved.setTitle("T");
        when(taskRepository.save(any(Task.class))).thenReturn(saved);

    Task result = taskService.createTask("T", "d", TaskStatus.PENDING, TaskPriority.MEDIUM, LocalDateTime.now().plusDays(1), "u@example.com");

        assertNotNull(result);
        assertEquals("T", result.getTitle());
        verify(taskRepository).save(any());
    }

    @Test
    void updateTask_dueDatePast_throws() {
        User user = new User();
        user.setEmail("u3@example.com");
    Task t = mock(Task.class);
    when(t.getId()).thenReturn(1L);
    when(t.getTitle()).thenReturn("old");
    user.setTasks(new java.util.ArrayList<>(List.of(t)));

    when(userRepository.findByEmail("u3@example.com")).thenReturn(Optional.of(user));

    assertThrows(Exception.class, () -> taskService.updateTask("u3@example.com", 1L, "n", "d", TaskStatus.PENDING, TaskPriority.LOW, LocalDateTime.now().minusDays(1)));
    }

    @Test
    void deleteTask_success() {
        User user = new User();
        user.setEmail("u4@example.com");
    Task t = mock(Task.class);
    when(t.getId()).thenReturn(7L);
    user.setTasks(new java.util.ArrayList<>(List.of(t)));
    when(userRepository.findByEmail("u4@example.com")).thenReturn(Optional.of(user));

    // simulate deletion
    taskService.deleteTask("u4@example.com", 7L);

    verify(taskRepository).delete(any(Task.class));
    verify(userRepository).save(user);
    }
}
