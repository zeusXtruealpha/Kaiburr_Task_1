# Task Management REST API

A Spring Boot application that provides a REST API.

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.6
- **Java Version**: Java 21
- **Database**: MongoDB
- **Build Tool**: Maven
- **Testing**: Spring Boot Test
- **Development Tools**: Lombok, Spring Boot DevTools

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **MongoDB** 
- **Git** (for cloning the repository)

### System Requirements
- **RAM**: Minimum 2GB available memory
- **Disk Space**: At least 500MB free space
- **Network**: Port 8080 available for the application
- **OS**: Windows, macOS, or Linux

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/zeusXtruealpha/Kaiburr_Task_1
cd demo
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Start MongoDB
```bash
# Windows
mongod

# macOS (with Homebrew)
brew services start mongodb-community

# Linux
sudo systemctl start mongod
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 5. Verify Installation
```bash
curl -X GET "http://localhost:8080/tasks"
```

You should receive an empty array `[]` if no tasks exist.

## ⚙️ Configuration

### Application Properties
The main configuration is in `src/main/resources/application.properties`:

```properties
# Application Configuration
spring.application.name=demo
server.port=8080

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=taskdb
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Data Models

#### Task
```json
{
  "id": "string",
  "name": "string", 
  "owner": "string",
  "command": "string",
  "taskExecutions": [
    {
      "id": "string",
      "startTime": "2023-04-21T15:51:42.276Z",
      "endTime": "2023-04-21T15:51:43.276Z", 
      "output": "string"
    }
  ]
}
```

#### TaskExecution
```json
{
  "id": "string",
  "startTime": "2023-04-21T15:51:42.276Z",
  "endTime": "2023-04-21T15:51:43.276Z",
  "output": "string"
}
```

### API Endpoints

#### 1. Get All Tasks
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

#### 2. Create/Update Task
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

#### 3. Delete Task
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

#### 4. Search Tasks by Name
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

#### 5. Execute Task
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

## 💡 Usage Examples

### Complete Workflow Example

1. **Create a task:**
```bash
curl -X PUT "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "task-001",
    "name": "System Information",
    "owner": "Admin",
    "command": "systeminfo"
  }'
```

2. **Get all tasks:**
```bash
curl -X GET "http://localhost:8080/tasks"
```

3. **Execute the task:**
```bash
curl -X PUT "http://localhost:8080/tasks/task-001/execute"
```

4. **Search for tasks:**
```bash
curl -X GET "http://localhost:8080/tasks/search?name=System"
```

5. **Get specific task with execution history:**
```bash
curl -X GET "http://localhost:8080/tasks?id=task-001"
```

6. **Delete the task:**
```bash
curl -X DELETE "http://localhost:8080/tasks/task-001"
```

### Sample Tasks

The `sample_requests.json` file contains example tasks you can use for testing:

```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!"
}
```

## 🔒 Security Features

### Command Validation
The API validates commands to prevent execution of dangerous operations. The following are blocked:

**File System Operations:**
- `rm`, `del`, `format`, `fdisk`, `mkfs`, `dd`

**System Operations:**
- `shutdown`, `reboot`, `halt`, `poweroff`, `init`

**Process Operations:**
- `killall`, `pkill`, `kill`

**Privilege Escalation:**
- `sudo`, `su`

**User Management:**
- `passwd`, `useradd`, `userdel`

**Permission Operations:**
- `chmod`, `chown`, `chgrp`

**Mount Operations:**
- `mount`, `umount`

**Shell Operators:**
- `>`, `>>`, `<`, `|`, `&`, `;`, `&&`, `||`

### Error Handling
The application includes comprehensive error handling with custom exceptions:

- `TaskNotFoundException`: When a requested task doesn't exist
- `UnsafeCommandException`: When a command fails security validation
- `GlobalExceptionHandler`: Centralized error handling with proper HTTP status codes

## 🗄️ Database Schema

### MongoDB Collections

#### Tasks Collection (`tasks`)
```javascript
{
  "_id": "ObjectId",
  "id": "string",           // Custom task ID
  "name": "string",         // Task name
  "owner": "string",        // Task owner
  "command": "string",      // Shell command
  "taskExecutions": [       // Array of execution history
    {
      "id": "string",
      "startTime": "Date",
      "endTime": "Date",
      "output": "string"
    }
  ]
}
```

#### Task Executions Collection (`taskExecutions`)
```javascript
{
  "_id": "ObjectId",
  "id": "string",           // Execution ID
  "startTime": "Date",      // Execution start time
  "endTime": "Date",        // Execution end time
  "output": "string"        // Command output
}
```

## 🧪 Testing

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskControllerIntegrationTest

# Run tests with coverage
mvn test jacoco:report
```

### Test Structure
- **Unit Tests**: `DemoApplicationTests.java`
- **Integration Tests**: `TaskControllerIntegrationTest.java`
- **Database Tests**: `MongoDBConnectionTest.java`

### Test Coverage
The test suite covers:
- All API endpoints
- Error handling scenarios
- Database operations
- Command validation
- Task execution functionality

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── kaiburr/
│   │           └── demo/
│   │               ├── controller/
│   │               │   └── TaskController.java      # REST API endpoints
│   │               ├── exception/
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   ├── TaskNotFoundException.java
│   │               │   └── UnsafeCommandException.java
│   │               ├── model/
│   │               │   ├── Task.java               # Task entity
│   │               │   └── TaskExecution.java      # Execution entity
│   │               ├── repository/
│   │               │   └── TaskRepository.java     # Data access layer
│   │               ├── service/
│   │               │   └── TaskService.java        # Business logic
│   │               └── DemoApplication.java        # Main application class
│   └── resources/
│       ├── application.properties                 # Configuration
│       ├── static/                               # Static resources
│       └── templates/                            # Template files
└── test/
    └── java/
        └── com/
            └── kaiburr/
                └── demo/
                    ├── DemoApplicationTests.java
                    ├── MongoDBConnectionTest.java
                    └── TaskControllerIntegrationTest.java
```

## 🔧 Development

### Development Setup

1. **IDE Configuration**: Use IntelliJ IDEA or Eclipse with Spring Boot support
2. **Code Style**: Follow Java conventions and Spring Boot best practices
3. **Git Hooks**: Consider setting up pre-commit hooks for code quality

### Building the Application
```bash
# Clean and compile
mvn clean compile

# Package the application
mvn clean package

# Run with Maven
mvn spring-boot:run

# Run the JAR file
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Development Tools
- **Spring Boot DevTools**: Automatic restart and live reload
- **Lombok**: Reduces boilerplate code
- **Maven Wrapper**: Consistent build environment

### Code Quality
- **Lombok**: Automatic getters/setters generation
- **Spring Boot Validation**: Input validation
- **Exception Handling**: Centralized error management
- **Logging**: Comprehensive debug logging

## 🐛 Troubleshooting

### Common Issues

#### 1. MongoDB Connection Issues
**Problem**: Application fails to connect to MongoDB
**Solution**: 
- Ensure MongoDB is running: `mongod`
- Check connection settings in `application.properties`
- Verify MongoDB is accessible on the configured port

#### 2. Port Already in Use
**Problem**: Port 8080 is already in use
**Solution**:
```bash
# Change port in application.properties
server.port=8081

# Or kill the process using port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

#### 3. Java Version Issues
**Problem**: Unsupported Java version
**Solution**:
- Ensure Java 21+ is installed
- Check JAVA_HOME environment variable
- Update Maven compiler plugin configuration

#### 4. Command Execution Failures
**Problem**: Commands fail to execute
**Solution**:
- Check if command is in the blocked list
- Verify command syntax
- Ensure the command exists on the system

### Debug Mode
Enable debug logging by adding to `application.properties`:
```properties
logging.level.com.kaiburr.demo=DEBUG
logging.level.org.springframework.data.mongodb=DEBUG
```

### Health Checks
Monitor application health:
```bash
# Check if application is running
curl -X GET "http://localhost:8080/tasks"

# Check MongoDB connection
curl -X GET "http://localhost:8080/actuator/health"
```

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/new-feature`
3. **Commit changes**: `git commit -am 'Add new feature'`
4. **Push to branch**: `git push origin feature/new-feature`
5. **Submit a Pull Request**

### Contribution Guidelines
- Follow existing code style and conventions
- Add tests for new functionality
- Update documentation as needed
- Ensure all tests pass before submitting

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the API documentation
- Examine the test cases for usage examples

---

**Happy Coding! 🚀**
