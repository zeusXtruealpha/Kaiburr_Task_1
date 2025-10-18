package com.kaiburr.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    
    private String name;
    private String owner;
    private String command;
    
    @JsonProperty("taskExecutions")
    @Field("taskExecutions")
    private List<TaskExecution> taskExecutions = new ArrayList<>();
    
    // Constructors
    public Task() {}
    
    public Task(String id, String name, String owner, String command) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.command = command;
        this.taskExecutions = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    
    public List<TaskExecution> getTaskExecutions() {
        return taskExecutions;
    }
    
    public void setTaskExecutions(List<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
    }
    
    public void addTaskExecution(TaskExecution taskExecution) {
        this.taskExecutions.add(taskExecution);
    }
}
