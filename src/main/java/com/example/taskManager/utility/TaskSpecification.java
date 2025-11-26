package com.example.taskManager.utility;

import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecification
{
    public static Specification<Task> hasPriority(TaskPriority priority)
    {
        return (root, query, criteriaBuilder) -> priority == null ? null : criteriaBuilder.equal(root.get("priority") , priority);
    }

    public static Specification<Task> hasStatus(TaskStatus status)
    {
        return (root, query, criteriaBuilder) -> status==null ? null : criteriaBuilder.equal(root.get("status") , status) ;
    }

    public static Specification<Task> dueBefore(LocalDateTime dueDate)
    {
        return (root, query, criteriaBuilder) -> dueDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("dueDate") , dueDate) ;
    }

    public static Specification<Task> assignedTo(User user)
    {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("assignedTo") , user) ;
    }
}
