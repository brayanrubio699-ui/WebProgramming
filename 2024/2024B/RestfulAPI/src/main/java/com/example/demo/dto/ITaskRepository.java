package com.example.demo.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Task;
import java.util.List;

@Repository
public interface ITaskRepository<Task, Integer> extends JpaRepository {

    List<Task> selectAll();
    Task getTask(int bookId);
    Task insertTask(Task newTask);
    Object updateTask(int taskId, Object task);
    Task deleteTask(int taskId);
        
}
