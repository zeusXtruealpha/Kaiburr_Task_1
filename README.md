# Task Management REST API - Niranjan Galla

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
mongod --port 27017 --dbpath "C:\Program Files\MongoDB\Server\8.2\data"
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


6. **Search for tasks using name**
```bash
GET "http://localhost:8080/tasks/tasks?name=Hello"
```
![WhatsApp Image 2025-10-19 at 01 58 11_ff1e7f95](https://github.com/user-attachments/assets/2a5ce115-dfe5-49a8-9edc-f98bb4d670b3)


7. **Search for tasks which are not there using name**
```bash
GET "http://localhost:8080/tasks/tasks?name=Niran"
```
![WhatsApp Image 2025-10-19 at 02 12 38_4b5114a1](https://github.com/user-attachments/assets/8ab08056-3b25-42d9-8936-5d695fdc63fb)


8. **Execute Task:**
```bash
PUT "http://localhost:8080/tasks/162/execute"
```
![WhatsApp Image 2025-10-19 at 02 04 32_5f2d756a](https://github.com/user-attachments/assets/0883de15-5906-4d47-9cbd-ff26e1f7c4fa)


9. **Try Executing Task which is not there:**
```bash
PUT "http://localhost:8080/tasks/113/execute"
```
![WhatsApp Image 2025-10-19 at 02 16 44_822a83d2](https://github.com/user-attachments/assets/3075674d-6fbc-4c73-877a-cba034da504a)


10. **Get specific task with execution history:**
```bash
GET "http://localhost:8080/tasks?id=123"
```
<img width="1801" height="845" alt="image" src="https://github.com/user-attachments/assets/877313f3-0349-4f8d-9b12-3febfd13354b" />


11. **Delete the task:**
```bash
DELETE "http://localhost:8080/tasks/456"
```
![WhatsApp Image 2025-10-19 at 02 11 08_bd4f5a86](https://github.com/user-attachments/assets/b4c8c40a-ed57-41bf-8702-db4b59b4bff4)


12. **Create a task with unsafe/malicious code:**
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


**MongoDB Image**
<img width="1920" height="1072" alt="image" src="https://github.com/user-attachments/assets/bd7fa068-d65a-4fc4-ada2-c2314e4a4470" />


## Security Features

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
- <img width="1555" height="411" alt="image" src="https://github.com/user-attachments/assets/a09d989b-ab05-4353-ba0a-95f2a2c22a36" />

- **Integration Tests**: `TaskControllerIntegrationTest.java`
- <img width="1656" height="365" alt="image" src="https://github.com/user-attachments/assets/192e74a9-fb28-494a-b0fb-5bbb75cce17e" />

- **Database Tests**: `MongoDBConnectionTest.java`
- <img width="1738" height="439" alt="image" src="https://github.com/user-attachments/assets/f2c08e17-202e-4cff-9c40-8f4f2d795dc4" />


## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── kaiburr/
│   │           └── demo/
│   │               ├── controller/
│   │               │   └── TaskController.java    
│   │               ├── exception/
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   ├── TaskNotFoundException.java
│   │               │   └── UnsafeCommandException.java
│   │               ├── model/
│   │               │   ├── Task.java               
│   │               │   └── TaskExecution.java      
│   │               ├── repository/
│   │               │   └── TaskRepository.java     
│   │               ├── service/
│   │               │   └── TaskService.java        
│   │               └── DemoApplication.java        
│   └── resources/
│       ├── application.properties                 
│       ├── static/                              
│       └── templates/                         
└── test/
    └── java/
        └── com/
            └── kaiburr/
                └── demo/
                    ├── DemoApplicationTests.java
                    ├── MongoDBConnectionTest.java
                    └── TaskControllerIntegrationTest.java
```

**IDE Configuration**: Used IntelliJ IDEA ULTIMATE 

### Building the Application
```bash
# Clean and compile
mvn clean compile

# Package the application
mvn clean package

# Run with Maven
mvn spring-boot:run
```
