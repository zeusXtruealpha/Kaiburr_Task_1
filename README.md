# Task Management REST API

A Spring Boot application that provides a REST API.

## Technology Stack

- **Backend Framework**: Spring Boot 3.5.6
- **Java Version**: Java 21
- **Database**: MongoDB
- **Build Tool**: Maven
- **Testing**: Spring Boot Test
- **Development Tools**: Lombok, Spring Boot DevTools
  <img width="1734" height="857" alt="image" src="https://github.com/user-attachments/assets/f0a824b1-01a3-42d8-b6a2-d424ffab4de5" />

## Prerequisites

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

## Installation & Setup

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

## Configuration

### Ports

```
server.port=8080
spring.data.mongodb.port=27017
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

1. **Create a task:**
```bash
PUT "http://localhost:8080/tasks" \
"Content-Type: application/json" \
 {
    "id": "162",
    "name": "Print Hi",
    "owner": "Niranjan",
    "command": "echo Hi"
  }'
```
![WhatsApp Image 2025-10-19 at 01 54 28_65085e32](https://github.com/user-attachments/assets/12945f4f-f942-48c6-8c78-cb5c64e64a12)

2. **Get all tasks:**
```bash
GET "http://localhost:8080/tasks"
```
<img width="1841" height="841" alt="image" src="https://github.com/user-attachments/assets/68b1b183-6912-4976-848d-faa8a521c29a" />

3. **Execute the task:**
```bash
PUT "http://localhost:8080/tasks/162/execute"
```
![WhatsApp Image 2025-10-19 at 02 04 32_94d21af0](https://github.com/user-attachments/assets/52a5c3b2-076a-4a45-8b59-6970eaf15e7d)

4. **Search for tasks using id**
```bash
GET "http://localhost:8080/tasks/tasks?id=123"
```
![WhatsApp Image 2025-10-19 at 01 55 45_886c991b](https://github.com/user-attachments/assets/e00b79ec-489a-4b54-b5f4-c0d6db8c2d03)

5. **Search for tasks using id which is not there**
```bash
GET "http://localhost:8080/tasks/tasks?id=999"
```
![WhatsApp Image 2025-10-19 at 01 56 14_777f4d0b](https://github.com/user-attachments/assets/5e84e913-13a1-4210-aa4b-89cc210f7d32)

4. **Search for tasks using name**
```bash
GET "http://localhost:8080/tasks/tasks?name=Hello"
```
![WhatsApp Image 2025-10-19 at 01 58 11_ff1e7f95](https://github.com/user-attachments/assets/2a5ce115-dfe5-49a8-9edc-f98bb4d670b3)

5. **Search for tasks which are not there using name**
```bash
GET "http://localhost:8080/tasks/tasks?name=Hello"
```
![WhatsApp Image 2025-10-19 at 02 12 38_4b5114a1](https://github.com/user-attachments/assets/8ab08056-3b25-42d9-8936-5d695fdc63fb)

6. **Execute Task:**
```bash
PUT "http://localhost:8080/tasks/162/execute"
```
![WhatsApp Image 2025-10-19 at 02 04 32_5f2d756a](https://github.com/user-attachments/assets/0883de15-5906-4d47-9cbd-ff26e1f7c4fa)

7. **Try Executing Task which is not there:**
```bash
PUT "http://localhost:8080/tasks/162/execute"
```
![WhatsApp Image 2025-10-19 at 02 16 44_822a83d2](https://github.com/user-attachments/assets/3075674d-6fbc-4c73-877a-cba034da504a)

8. **Get specific task with execution history:**
```bash
GET "http://localhost:8080/tasks?id=123"
```
<img width="1801" height="845" alt="image" src="https://github.com/user-attachments/assets/877313f3-0349-4f8d-9b12-3febfd13354b" />

9. **Delete the task:**
```bash
DELETE "http://localhost:8080/tasks/task-001"
```
![WhatsApp Image 2025-10-19 at 02 11 08_bd4f5a86](https://github.com/user-attachments/assets/b4c8c40a-ed57-41bf-8702-db4b59b4bff4)

10. **Create a task with unsafe/malicious code:**
```bash
PUT "http://localhost:8080/tasks" \
"Content-Type: application/json" \
 {
    "id": "789",
    "name": "DeleteAll",
    "owner": "Gowthamee",
    "command": "rm -rf /"
  }'
```
![WhatsApp Image 2025-10-19 at 02 07 06_ccf57a21](https://github.com/user-attachments/assets/2e5aac21-4fc6-435c-9837-878b2fca05bd)


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


### MongoDB Collections


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
