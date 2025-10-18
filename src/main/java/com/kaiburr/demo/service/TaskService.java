package com.kaiburr.demo.service;

import com.kaiburr.demo.exception.TaskNotFoundException;
import com.kaiburr.demo.exception.UnsafeCommandException;
import com.kaiburr.demo.model.Task;
import com.kaiburr.demo.model.TaskExecution;
import com.kaiburr.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    // Dangerous commands that should be blocked
    private static final String[] DANGEROUS_COMMANDS = {
        "rm", "del", "format", "fdisk", "mkfs", "dd", "shutdown", "reboot",
        "halt", "poweroff", "init", "killall", "pkill", "kill", "sudo",
        "su", "passwd", "useradd", "userdel", "chmod", "chown", "chgrp",
        "mount", "umount", "umount", ">", ">>", "<", "|", "&", ";", "&&", "||"
    };
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Optional<Task> getTaskById(String id) {
        return taskRepository.findById(id);
    }
    
    public List<Task> getTasksByName(String name) {
        return taskRepository.findByNameContaining(name);
    }
    
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }
    
    public boolean taskExists(String id) {
        return taskRepository.existsById(id);
    }
    
    public boolean isCommandSafe(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }
        
        String lowerCommand = command.toLowerCase().trim();
        
        for (String dangerous : DANGEROUS_COMMANDS) {
            if (lowerCommand.contains(dangerous)) {
                return false;
            }
        }
        
        return true;
    }
    
    public TaskExecution executeTask(String taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        
        if (!taskOpt.isPresent()) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }
        
        Task task = taskOpt.get();
        if (!isCommandSafe(task.getCommand())) {
            throw new UnsafeCommandException("Command contains unsafe operations: " + task.getCommand());
        }
        
        Date startTime = new Date();
        String output = "";
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd", "/c", task.getCommand());
            
            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            
            int exitCode = process.waitFor();
            output = outputBuilder.toString();
            
            if (exitCode != 0) {
                output = "Command failed with exit code: " + exitCode + "\n" + output;
            }
            
        } catch (Exception e) {
            output = "Error executing command: " + e.getMessage();
        }
        
        Date endTime = new Date();
        String executionId = UUID.randomUUID().toString();
        TaskExecution execution = new TaskExecution(executionId, startTime, endTime, output);
        
        task.addTaskExecution(execution);
        taskRepository.save(task);
        
        return execution;
    }
}

