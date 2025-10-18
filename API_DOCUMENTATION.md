# Task Management REST API Documentation

## Overview
This REST API provides endpoints for managing tasks that represent shell commands that can be executed in a Kubernetes pod. Each task contains metadata and execution history.

## Base URL
```
http://localhost:8080
```

## Data Models

### Task
```json
{
  "id": "string",
  "name": "string", 
  "owner": "string",
  "command": "string",
  "taskExecutions": [
    {
      "startTime": "2023-04-21T15:51:42.276Z",
      "endTime": "2023-04-21T15:51:43.276Z", 
      "output": "string"
    }
  ]
}
```

### TaskExecution
```json
{
  "startTime": "2023-04-21T15:51:42.276Z",
  "endTime": "2023-04-21T15:51:43.276Z",
  "output": "string"
}
```

## API Endpoints

### 1. Get All Tasks
**GET** `/tasks`

Returns all tasks in the system.

**Query Parameters:**
- `id` (optional): If provided, returns a single task with the specified ID

**Response:**
- `200 OK`: Returns array of tasks or single task
- `404 Not Found`: Task with specified ID not found

**Example:**
```bash
curl -X GET "http://localhost:8080/tasks"
curl -X GET "http://localhost:8080/tasks?id=123"
```

### 2. Create/Update Task
**PUT** `/tasks`

Creates a new task or updates an existing one.

**Request Body:**
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith", 
  "command": "echo Hello World!"
}
```

**Response:**
- `200 OK`: Task created/updated successfully
- `400 Bad Request`: Invalid input or unsafe command

**Example:**
```bash
curl -X PUT "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!"
  }'
```

### 3. Delete Task
**DELETE** `/tasks/{id}`

Deletes a task by its ID.

**Path Parameters:**
- `id`: Task ID to delete

**Response:**
- `200 OK`: Task deleted successfully
- `404 Not Found`: Task not found

**Example:**
```bash
curl -X DELETE "http://localhost:8080/tasks/123"
```

### 4. Search Tasks by Name
**GET** `/tasks/search?name={name}`

Finds tasks whose names contain the specified string (case-insensitive).

**Query Parameters:**
- `name`: String to search for in task names

**Response:**
- `200 OK`: Returns array of matching tasks
- `404 Not Found`: No tasks found

**Example:**
```bash
curl -X GET "http://localhost:8080/tasks/search?name=Hello"
```

### 5. Execute Task
**PUT** `/tasks/{id}/execute`

Executes a task's shell command and stores the execution result.

**Path Parameters:**
- `id`: Task ID to execute

**Response:**
- `200 OK`: Returns TaskExecution object with execution details
- `404 Not Found`: Task not found
- `400 Bad Request`: Unsafe command detected

**Example:**
```bash
curl -X PUT "http://localhost:8080/tasks/123/execute"
```

## Security Features

### Command Validation
The API validates commands to prevent execution of dangerous operations. The following are blocked:
- File system operations: `rm`, `del`, `format`, `fdisk`, `mkfs`, `dd`
- System operations: `shutdown`, `reboot`, `halt`, `poweroff`, `init`
- Process operations: `killall`, `pkill`, `kill`
- Privilege escalation: `sudo`, `su`
- User management: `passwd`, `useradd`, `userdel`
- Permission operations: `chmod`, `chown`, `chgrp`
- Mount operations: `mount`, `umount`
- Shell operators: `>`, `>>`, `<`, `|`, `&`, `;`, `&&`, `||`

## Error Responses

### 400 Bad Request
```json
{
  "error": "Unsafe command",
  "message": "Command contains unsafe operations: rm -rf /"
}
```

### 404 Not Found
```json
{
  "error": "Task not found", 
  "message": "Task not found with id: 999"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal server error",
  "message": "Database connection failed"
}
```

## Sample Workflow

1. **Create a task:**
```bash
curl -X PUT "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!"
  }'
```

2. **Get all tasks:**
```bash
curl -X GET "http://localhost:8080/tasks"
```

3. **Execute the task:**
```bash
curl -X PUT "http://localhost:8080/tasks/123/execute"
```

4. **Search for tasks:**
```bash
curl -X GET "http://localhost:8080/tasks/search?name=Hello"
```

5. **Delete the task:**
```bash
curl -X DELETE "http://localhost:8080/tasks/123"
```

## Database Configuration

The application uses MongoDB for data persistence. Configuration in `application.properties`:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=taskdb
```

If MongoDB is not available, the application falls back to in-memory storage for demonstration purposes.

## Running the Application

1. **Prerequisites:**
   - Java 21+
   - Maven 3.6+
   - MongoDB (optional, falls back to in-memory storage)

2. **Start the application:**
```bash
mvn spring-boot:run
```

3. **Access the API:**
   - Base URL: `http://localhost:8080`
   - All endpoints are under `/tasks`

## Testing

Run the test suite:
```bash
mvn test
```

The application includes comprehensive integration tests that demonstrate all API functionality.
