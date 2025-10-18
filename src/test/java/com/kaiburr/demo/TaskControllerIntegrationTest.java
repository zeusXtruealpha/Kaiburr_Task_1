package com.kaiburr.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiburr.demo.model.Task;
import com.kaiburr.demo.model.TaskExecution;
import com.kaiburr.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TaskRepository taskRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        taskRepository.deleteAll();
    }

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        
        mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Print Hello"))
                .andExpect(jsonPath("$.owner").value("John Smith"))
                .andExpect(jsonPath("$.command").value("echo Hello World!"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        // Create a task first
        Task task = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        taskRepository.save(task);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("123"));
    }

    @Test
    void testGetTaskById() throws Exception {
        // Create a task first
        Task task = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        taskRepository.save(task);

        mockMvc.perform(get("/tasks?id=123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.name").value("Print Hello"));
    }

    @Test
    void testGetTaskByIdNotFound() throws Exception {
        mockMvc.perform(get("/tasks?id=999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchTasksByName() throws Exception {
        // Create tasks first
        Task task1 = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        Task task2 = new Task("456", "Hello World Task", "Jane Doe", "echo Hello World!");
        taskRepository.save(task1);
        taskRepository.save(task2);

        mockMvc.perform(get("/tasks/search?name=Hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testExecuteTask() throws Exception {
        // Create a task first
        Task task = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        taskRepository.save(task);

        mockMvc.perform(put("/tasks/123/execute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output").exists())
                .andExpect(jsonPath("$.startTime").exists())
                .andExpect(jsonPath("$.endTime").exists());
    }

    @Test
    void testDeleteTask() throws Exception {
        // Create a task first
        Task task = new Task("123", "Print Hello", "John Smith", "echo Hello World!");
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));
    }

    @Test
    void testUnsafeCommand() throws Exception {
        Task task = new Task("123", "Dangerous Task", "John Smith", "rm -rf /");
        
        mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unsafe command"));
    }
}
