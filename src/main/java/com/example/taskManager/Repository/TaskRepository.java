package com.example.taskManager.Repository;

import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> , JpaSpecificationExecutor<Task> {

    List<Task> findByAssignedTo(User user);

    //JpaSpecificationExecutor<> - enable specifications(for multiple filters) on Jpa query
}
