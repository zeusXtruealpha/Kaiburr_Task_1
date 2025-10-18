package com.kaiburr.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "taskExecutions")
public class TaskExecution {
    @Id
    private String id;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date endTime;
    
    private String output;
    
    // Constructors
    public TaskExecution() {}
    
    public TaskExecution(Date startTime, Date endTime, String output) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.output = output;
    }
    
    public TaskExecution(String id, Date startTime, Date endTime, String output) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.output = output;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public String getOutput() {
        return output;
    }
    
    public void setOutput(String output) {
        this.output = output;
    }
}
