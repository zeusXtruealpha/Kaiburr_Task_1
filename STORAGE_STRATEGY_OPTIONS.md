# Storage Strategy Options for MongoDB Failures

## Current Implementation: In-Memory Fallback

### ✅ **What We Have:**
```java
// In-memory storage for demo purposes when MongoDB is not available
private static final java.util.Map<String, Task> inMemoryTasks = new java.util.concurrent.ConcurrentHashMap<>();

public List<Task> getAllTasks() {
    try {
        return taskRepository.findAll(); // MongoDB
    } catch (Exception e) {
        return new ArrayList<>(inMemoryTasks.values()); // Fallback
    }
}
```

### **Pros:**
- ✅ API remains functional
- ✅ No application crashes
- ✅ Good for demos and development
- ✅ Transparent to API consumers

### **Cons:**
- ❌ Data lost on restart
- ❌ No persistence
- ❌ Memory limitations
- ❌ Not production-ready

## Alternative Strategy 1: Fail Fast (Recommended for Production)

### **Implementation:**
```java
public List<Task> getAllTasks() {
    return taskRepository.findAll(); // Let it fail if MongoDB is down
}

// With proper error handling in controller
@GetMapping
public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
    try {
        if (id != null && !id.isEmpty()) {
            Optional<Task> task = taskService.getTaskById(id);
            return task.isPresent() ? ResponseEntity.ok(task.get()) : ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(taskService.getAllTasks());
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body("Database service unavailable. Please try again later.");
    }
}
```

### **Pros:**
- ✅ Clear error messages
- ✅ Forces proper infrastructure setup
- ✅ Production-ready approach
- ✅ No data inconsistency

### **Cons:**
- ❌ API unavailable when MongoDB is down
- ❌ Requires proper monitoring and alerting

## Alternative Strategy 2: File-Based Fallback

### **Implementation:**
```java
@Service
public class TaskService {
    
    private static final String FALLBACK_FILE = "tasks-backup.json";
    
    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            return loadFromFile();
        }
    }
    
    private List<Task> loadFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(FALLBACK_FILE);
            if (file.exists()) {
                return Arrays.asList(mapper.readValue(file, Task[].class));
            }
        } catch (Exception e) {
            System.err.println("Failed to load from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    private void saveToFile(List<Task> tasks) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(FALLBACK_FILE), tasks);
        } catch (Exception e) {
            System.err.println("Failed to save to file: " + e.getMessage());
        }
    }
}
```

### **Pros:**
- ✅ Data persistence across restarts
- ✅ Better than in-memory
- ✅ Simple implementation

### **Cons:**
- ❌ File I/O overhead
- ❌ Not suitable for high concurrency
- ❌ Manual cleanup required

## Alternative Strategy 3: Hybrid Approach (Best of Both Worlds)

### **Implementation:**
```java
@Service
public class TaskService {
    
    private static final java.util.Map<String, Task> inMemoryTasks = new java.util.concurrent.ConcurrentHashMap<>();
    private static final String FALLBACK_FILE = "tasks-backup.json";
    private boolean mongoAvailable = true;
    
    @PostConstruct
    public void initializeFallback() {
        if (!isMongoAvailable()) {
            loadFromFile();
        }
    }
    
    public List<Task> getAllTasks() {
        if (mongoAvailable) {
            try {
                return taskRepository.findAll();
            } catch (Exception e) {
                mongoAvailable = false;
                System.out.println("MongoDB unavailable, switching to fallback storage");
                return new ArrayList<>(inMemoryTasks.values());
            }
        } else {
            return new ArrayList<>(inMemoryTasks.values());
        }
    }
    
    public Task saveTask(Task task) {
        if (mongoAvailable) {
            try {
                Task saved = taskRepository.save(task);
                inMemoryTasks.put(task.getId(), task); // Keep in-memory cache
                return saved;
            } catch (Exception e) {
                mongoAvailable = false;
                System.out.println("MongoDB unavailable, using fallback storage");
            }
        }
        
        inMemoryTasks.put(task.getId(), task);
        saveToFile(new ArrayList<>(inMemoryTasks.values()));
        return task;
    }
    
    private boolean isMongoAvailable() {
        try {
            taskRepository.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

### **Pros:**
- ✅ Automatic MongoDB health checking
- ✅ Persistent fallback storage
- ✅ Seamless switching
- ✅ Production-ready with monitoring

### **Cons:**
- ❌ More complex implementation
- ❌ Requires file system access

## Recommendation for Your Use Case

### **For Development/Demo:**
**Keep current in-memory fallback** - It's perfect for:
- ✅ Demonstrations
- ✅ Development without MongoDB
- ✅ Testing scenarios
- ✅ Quick setup

### **For Production:**
**Switch to Fail Fast approach** with proper monitoring:
- ✅ Clear error messages
- ✅ Forces proper infrastructure
- ✅ Production-ready
- ✅ Better user experience

## Implementation Decision

**Current implementation is GOOD for your requirements because:**

1. **Assessment Context**: This appears to be for a technical assessment/demo
2. **Flexibility**: Works with or without MongoDB
3. **Simplicity**: Easy to set up and demonstrate
4. **Functionality**: All API endpoints work regardless of MongoDB status

**Recommendation: KEEP the current implementation** for this use case, but add better logging:

```java
public Task saveTask(Task task) {
    try {
        return taskRepository.save(task);
    } catch (Exception e) {
        System.out.println("MongoDB not available, storing in memory: " + e.getMessage());
        inMemoryTasks.put(task.getId(), task);
        return task;
    }
}
```

This approach is perfect for:
- ✅ Technical assessments
- ✅ Demonstrations
- ✅ Development environments
- ✅ Testing scenarios
