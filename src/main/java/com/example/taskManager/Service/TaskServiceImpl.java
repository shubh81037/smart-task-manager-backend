package com.example.taskManager.Service;

import com.example.taskManager.Repository.TaskRepository;
import com.example.taskManager.Repository.UserRepository;
import com.example.taskManager.exception.TaskNotFound;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import com.example.taskManager.utility.TaskSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Task createTask(@NotBlank String title, String description, TaskStatus status, TaskPriority priority, @Future LocalDateTime dueDate, @NonNull String username) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setPriority(priority);
        task.setDueDate(dueDate);

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username is not found , please try to login again"));

        task.setAssignedTo(user);

        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getTask(@NonNull String username, int page, int size, TaskStatus status, LocalDateTime dueDate, TaskPriority priority) throws TaskNotFound {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username is not found , please try to login again"));

        Specification<Task> specification = TaskSpecification.assignedTo(user)
                                                         .and(TaskSpecification.dueBefore(dueDate))
                                                         .and(TaskSpecification.hasStatus(status))
                                                         .and(TaskSpecification.hasPriority(priority));

        //size - indicate how many element should be there on a single page , ex size = 2  means per page there will be 2 elements
        //page - simply indicate page number
        Pageable pageable = PageRequest.of(page ,size , Sort.by("dueDate").ascending() ); // assignedTo - user variable

        return taskRepository.findAll(specification , pageable) ;
    }

    @Transactional
    @Override
    public Task updateTask(@NonNull String username, @NonNull Long id, @NotBlank String title, String description, TaskStatus status, TaskPriority priority, @Future LocalDateTime dueDate) throws Exception {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Task reqTask = user.getTasks().stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(() -> new InputMismatchException("Given task Id is not match to User's tasks id's "));

        LocalDateTime now = LocalDateTime.now();
        if (dueDate.isBefore(now)) {
            throw new InputMismatchException("dueDate should be of future time");
        }

        reqTask.setTitle(title);
        reqTask.setDescription(description);
        reqTask.setStatus(status);
        reqTask.setPriority(priority);
        reqTask.setDueDate(dueDate);
        return taskRepository.save(reqTask);
    }

    @Transactional
    @Override
    public void deleteTask(@NonNull String username, @NonNull Long id) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Task reqTask = user.getTasks().stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(() -> new InputMismatchException("Given task Id is not match to User's tasks id's "));
        user.getTasks().remove(reqTask);
        taskRepository.delete(reqTask);
        userRepository.save(user);
    }
}
