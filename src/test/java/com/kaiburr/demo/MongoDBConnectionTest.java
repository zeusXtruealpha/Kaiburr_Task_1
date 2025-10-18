package com.kaiburr.demo;

import com.kaiburr.demo.model.Task;
import com.kaiburr.demo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.host=localhost",
    "spring.data.mongodb.port=27017",
    "spring.data.mongodb.database=taskdb"
})
public class MongoDBConnectionTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testMongoDBConnection() {
        // Create a test task
        Task task = new Task("test-123", "MongoDB Test", "Test User", "echo MongoDB is working!");
        
        // Save to MongoDB
        Task savedTask = taskRepository.save(task);
        
        // Verify it was saved
        assertNotNull(savedTask);
        assertEquals("test-123", savedTask.getId());
        assertEquals("MongoDB Test", savedTask.getName());
        
        // Find the task
        Task foundTask = taskRepository.findById("test-123").orElse(null);
        assertNotNull(foundTask);
        assertEquals("MongoDB Test", foundTask.getName());
        
        // Clean up
        taskRepository.deleteById("test-123");
        
        // Verify deletion
        assertFalse(taskRepository.existsById("test-123"));
    }
}
