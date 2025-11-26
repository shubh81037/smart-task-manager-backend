package com.example.taskManager.Controller;

import com.example.taskManager.Service.TaskService;
import com.example.taskManager.dto.CreateTaskDTO;
import com.example.taskManager.dto.TaskResponseDTO;
import com.example.taskManager.exception.TaskNotFound;
import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
// InvalidTypeException not required in controller after changes
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Tasks", description = "Endpoints for managing user tasks")
@SecurityRequirement( name = "bearerAuth")      //for swagger to know what methods needs Jwt
@RestController
@RequestMapping("api/tasks")
public class TaskController
{
    private final TaskService taskService ;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    @Operation(summary = "Create a new task", description = "Requires JWT. Creates a task for the authenticated user.")
    @PostMapping     // we need to send JWT token as a Header and spring will handle via @AuthenticationPrincipal and get & send user object automatically into method
    public ResponseEntity<TaskResponseDTO> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task creation payload",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateTaskDTO.class))
    )
            @RequestBody @Valid CreateTaskDTO request , @AuthenticationPrincipal @NonNull UserDetails userDetails)
    {
        Task task = taskService.createTask(request.getTitle() , request.getDescription() , request.getStatus() , request.getPriority() , request.getDueDate() ,userDetails.getUsername()) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToTaskResponseDto(task)) ;
    }



    @Operation(summary = "Get paginated tasks", description = "Requires JWT. Returns tasks with optional filters and pagination.")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getTask( @Parameter(description = "Page number (0-based)")  @RequestParam(defaultValue = "0") int page,
                                                        @Parameter(description = "Page size") @RequestParam(defaultValue = "5") int size,
                                                        @Parameter(description = "Filter by status") @RequestParam(required = false) TaskStatus status,
                                                        @Parameter(description = "Filter by due date") @RequestParam(required = false) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
                                                        @Parameter(description = "Filter by priority") @RequestParam(required = false)TaskPriority priority,
                                                        @AuthenticationPrincipal @NonNull UserDetails userDetails) throws TaskNotFound
    {
        Page<Task> taskPage  = taskService.getTask(userDetails.getUsername() , page , size ,status , dueDate , priority ) ;
//        Page<TaskResponseDTO> response = taskPage.map(this::convertToTaskResponseDto) ;

        Map<String , Object>  objectsMap = new HashMap<>() ;
        objectsMap.put("tasks" , taskPage.map(this::convertToTaskResponseDto).getContent());
        objectsMap.put("currentPage" , taskPage.getNumber()) ;
        objectsMap.put("totalPage" , taskPage.getTotalPages()) ;
        objectsMap.put("totalItems" , taskPage.getTotalElements());

        return ResponseEntity.ok(objectsMap) ;
    }



    @Operation(summary = "Update a task",description = "Requires JWT. Updates a task owned by the authenticated user.")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask( @Parameter(description = "Task ID") @PathVariable @NonNull Long id ,@RequestBody CreateTaskDTO request,@AuthenticationPrincipal @NonNull UserDetails userDetails) throws TaskNotFound, Exception {
        Task task = taskService.updateTask(userDetails.getUsername() , id ,request.getTitle() , request.getDescription() , request.getStatus() , request.getPriority() , request.getDueDate()) ;
        TaskResponseDTO response = convertToTaskResponseDto(task);

        return ResponseEntity.ok(response) ;
    }


    @Operation(summary = "Delete a task",description = "Requires JWT. Deletes a task owned by the authenticated user.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@Parameter(description = "Task ID") @PathVariable @NonNull Long id,@AuthenticationPrincipal @NonNull UserDetails userDetails) throws TaskNotFound {
        taskService.deleteTask(userDetails.getUsername() , id) ;
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build() ;
    }

    public TaskResponseDTO convertToTaskResponseDto(Task task)
    {
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId( task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setDueDate(task.getDueDate());
        responseDTO.setStatus(task.getStatus());
        responseDTO.setAssignedToEmail(task.getAssignedTo().getEmail());
        responseDTO.setPriority(task.getPriority());

        return responseDTO ;
    }

}

/*Testing - if i delete the user , all the task associated with him also deleted , but roles should not
 *
 *         - insert Admin manually in DB , test roles functions and user functions
 * - On delete cascade - not implemented setting in database
 **/
/**update  and delete method needs to be implemented for task
 * role controller and service needs to be added
 * user update and delete methods needs to be added
 * functionality - only admin can view all the task of the user and can add the roles
 * make the interface of all the service layer class
 * test the functionality */


