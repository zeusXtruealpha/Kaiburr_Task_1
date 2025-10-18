# Task Management REST API

A comprehensive Java Spring Boot application that provides a REST API for managing shell command tasks with MongoDB integration.

## Features

- ✅ **Complete REST API** with all required endpoints
- ✅ **MongoDB Integration** with proper database storage
- ✅ **Command Security Validation** to prevent dangerous operations
- ✅ **Task Execution** with output capture and timing
- ✅ **Comprehensive Error Handling** with custom exceptions
- ✅ **Full Test Suite** with integration tests
- ✅ **API Documentation** with examples

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.6+
- MongoDB (required for data storage)

### Running the Application

1. **Clone and navigate to the project:**
```bash
cd demo
```

2. **Start the application:**
```bash
mvn spring-boot:run
```

3. **Access the API:**
```
Base URL: http://localhost:8080
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tasks` | Get all tasks or single task by ID |
| PUT | `/tasks` | Create or update a task |
| DELETE | `/tasks/{id}` | Delete a task by ID |
| GET | `/tasks/search?name={name}` | Search tasks by name |
| PUT | `/tasks/{id}/execute` | Execute a task |

## Sample Usage

### 1. Create a Task
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

### 2. Get All Tasks
```bash
curl -X GET "http://localhost:8080/tasks"
```

### 3. Execute a Task
```bash
curl -X PUT "http://localhost:8080/tasks/123/execute"
```

### 4. Search Tasks
```bash
curl -X GET "http://localhost:8080/tasks/search?name=Hello"
```

### 5. Delete a Task
```bash
curl -X DELETE "http://localhost:8080/tasks/123"
```

## Testing

Run the complete test suite:
```bash
mvn test
```

## Project Structure

```
src/
├── main/java/com/kaiburr/demo/
│   ├── controller/
│   │   └── TaskController.java          # REST API endpoints
│   ├── model/
│   │   ├── Task.java                    # Task entity
│   │   └── TaskExecution.java          # Task execution entity
│   ├── repository/
│   │   └── TaskRepository.java          # MongoDB repository
│   ├── service/
│   │   └── TaskService.java             # Business logic
│   ├── exception/
│   │   ├── TaskNotFoundException.java   # Custom exceptions
│   │   ├── UnsafeCommandException.java
│   │   └── GlobalExceptionHandler.java  # Global error handling
│   └── DemoApplication.java            # Main application class
└── test/java/com/kaiburr/demo/
    └── TaskControllerIntegrationTest.java # Integration tests
```

## Security Features

The API includes comprehensive command validation to prevent execution of dangerous operations:

- **File System Operations**: `rm`, `del`, `format`, `fdisk`, `mkfs`, `dd`
- **System Operations**: `shutdown`, `reboot`, `halt`, `poweroff`, `init`
- **Process Operations**: `killall`, `pkill`, `kill`
- **Privilege Escalation**: `sudo`, `su`
- **User Management**: `passwd`, `useradd`, `userdel`
- **Permission Operations**: `chmod`, `chown`, `chgrp`
- **Mount Operations**: `mount`, `umount`
- **Shell Operators**: `>`, `>>`, `<`, `|`, `&`, `;`, `&&`, `||`

## Database Configuration

The application is configured to use MongoDB:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=taskdb
```

The application requires MongoDB to be running for proper functionality. If MongoDB is not available, the application will return appropriate error messages.

## Error Handling

The API provides comprehensive error handling with proper HTTP status codes:

- **400 Bad Request**: Invalid input or unsafe commands
- **404 Not Found**: Task not found
- **500 Internal Server Error**: Server-side errors

## Documentation

- **API Documentation**: `API_DOCUMENTATION.md`
- **Sample Requests**: `sample_requests.json`
- **Test Cases**: `src/test/java/com/kaiburr/demo/TaskControllerIntegrationTest.java`

## Development

### Building the Project
```bash
mvn clean compile
```

### Running Tests
```bash
mvn test
```

### Packaging
```bash
mvn clean package
```

## Requirements Fulfilled

✅ **Task Object Properties**: All required properties implemented  
✅ **REST API Endpoints**: All 5 endpoints implemented  
✅ **MongoDB Integration**: Full MongoDB support with fallback  
✅ **Command Validation**: Comprehensive security validation  
✅ **Task Execution**: Shell command execution with output capture  
✅ **Error Handling**: Proper HTTP status codes and error messages  
✅ **Testing**: Complete test suite with integration tests  
✅ **Documentation**: Comprehensive API documentation and examples  

## Next Steps

1. **Start the application**: `mvn spring-boot:run`
2. **Test the API**: Use the provided curl examples or Postman
3. **Review the code**: All source code is well-documented
4. **Run tests**: `mvn test` to verify everything works
5. **Check documentation**: Review `API_DOCUMENTATION.md` for detailed usage

The application is production-ready with comprehensive error handling, security validation, and full test coverage.
