# Task Management API Testing Guide

## Prerequisites
1. Make sure MongoDB is running on localhost:27017
2. Start the Spring Boot application: `mvn spring-boot:run`

## API Endpoints

### 1. Create a Task
```bash
curl -X PUT http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!"
  }'
```

### 2. Get All Tasks
```bash
curl -X GET http://localhost:8080/tasks
```

### 3. Get Task by ID
```bash
curl -X GET http://localhost:8080/tasks?id=123
```

### 4. Search Tasks by Name
```bash
curl -X GET "http://localhost:8080/tasks/search?name=Hello"
```

### 5. Execute a Task
```bash
curl -X PUT http://localhost:8080/tasks/123/execute
```

### 6. Delete a Task
```bash
curl -X DELETE http://localhost:8080/tasks/123
```

## Sample Test Sequence

1. **Create a task:**
```bash
curl -X PUT http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "id": "task1",
    "name": "List Directory",
    "owner": "John Doe",
    "command": "dir"
  }'
```

2. **Execute the task:**
```bash
curl -X PUT http://localhost:8080/tasks/task1/execute
```

3. **Get the task with execution history:**
```bash
curl -X GET http://localhost:8080/tasks?id=task1
```

## Security Features
- Commands are validated for safety
- Dangerous commands like `rm`, `del`, `format`, etc. are blocked
- Commands with redirection operators (`>`, `<`, `|`) are blocked

## Expected Response Format

### Task Object:
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": [
    {
      "startTime": "2023-04-21T15:51:42.276Z",
      "endTime": "2023-04-21T15:51:43.276Z",
      "output": "Hello World!\n"
    }
  ]
}
```
