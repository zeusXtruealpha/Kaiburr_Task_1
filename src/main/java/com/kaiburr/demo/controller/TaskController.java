package com.kaiburr.demo.controller;

import com.kaiburr.demo.exception.TaskNotFoundException;
import com.kaiburr.demo.exception.UnsafeCommandException;
import com.kaiburr.demo.model.Task;
import com.kaiburr.demo.model.TaskExecution;
import com.kaiburr.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    // GET /tasks - return all tasks or single task by id parameter
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null && !id.isEmpty()) {
            Optional<Task> task = taskService.getTaskById(id);
            if (task.isPresent()) {
                return ResponseEntity.ok(task.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<Task> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        }
    }
    
    // PUT /tasks - create or update a task
    @PutMapping
    public ResponseEntity<?> createOrUpdateTask(@RequestBody Task task) {
        // Validate required fields
        if (task.getId() == null || task.getId().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Error: Task ID is required");
        }
        if (task.getName() == null || task.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Error: Task name is required");
        }
        if (task.getCommand() == null || task.getCommand().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Error: Task command is required");
        }
        
        // Validate command safety
        if (!taskService.isCommandSafe(task.getCommand())) {
            throw new UnsafeCommandException("Command contains unsafe operations. Please use safe commands only.");
        }
        
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.ok(savedTask);
    }
    
    // DELETE /tasks/{id} - delete a task by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        if (taskService.taskExists(id)) {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET /tasks/search?name={name} - find tasks by name
    @GetMapping("/search")
    public ResponseEntity<?> searchTasksByName(@RequestParam String name) {
        List<Task> tasks = taskService.getTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(tasks);
        }
    }
    
    // PUT /tasks/{id}/execute - execute a task
    @PutMapping("/{id}/execute")
    public ResponseEntity<?> executeTask(@PathVariable String id) {
        TaskExecution execution = taskService.executeTask(id);
        return ResponseEntity.ok(execution);
    }
}
