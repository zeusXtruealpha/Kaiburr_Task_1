# Git Upload and Running Guide

## 📁 **Files to Upload to Git**

### **✅ ESSENTIAL FILES (Must Upload):**

#### **1. Source Code:**
```
src/
├── main/
│   ├── java/com/kaiburr/demo/
│   │   ├── controller/TaskController.java
│   │   ├── DemoApplication.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── TaskNotFoundException.java
│   │   │   └── UnsafeCommandException.java
│   │   ├── model/
│   │   │   ├── Task.java
│   │   │   └── TaskExecution.java
│   │   ├── repository/TaskRepository.java
│   │   └── service/TaskService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/kaiburr/demo/
        ├── DemoApplicationTests.java
        ├── MongoDBConnectionTest.java
        └── TaskControllerIntegrationTest.java
```

#### **2. Configuration Files:**
```
pom.xml                    # Maven configuration
mvnw                       # Maven wrapper (Unix)
mvnw.cmd                   # Maven wrapper (Windows)
```

#### **3. Documentation (Optional but Recommended):**
```
README.md                  # Main documentation
API_DOCUMENTATION.md       # API documentation
test-requests.http         # HTTP test requests
sample_requests.json       # Sample API requests
```

### **❌ FILES TO EXCLUDE (Don't Upload):**

#### **1. Build Artifacts:**
```
target/                    # Maven build directory
*.class                    # Compiled Java files
```

#### **2. IDE Files:**
```
.idea/                     # IntelliJ IDEA files
*.iml                      # IntelliJ module files
.vscode/                   # VS Code files
```

#### **3. OS Files:**
```
.DS_Store                  # macOS files
Thumbs.db                  # Windows files
```

## 🚀 **How to Run the Application**

### **Prerequisites:**
1. **Java 21+** installed
2. **Maven 3.6+** installed
3. **MongoDB** running on localhost:27017

### **Step 1: Start MongoDB**

**Option A: Using Docker (Recommended):**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

**Option B: Install MongoDB Community Server:**
- Download from: https://www.mongodb.com/try/download/community
- Install and start MongoDB service

### **Step 2: Clone and Setup**

```bash
# Clone your repository
git clone <your-repository-url>
cd demo

# Verify Java version
java -version

# Verify Maven version
mvn -version
```

### **Step 3: Run the Application**

**Method 1: Using Maven Wrapper (Recommended):**
```bash
# On Windows
./mvnw.cmd spring-boot:run

# On Unix/Linux/macOS
./mvnw spring-boot:run
```

**Method 2: Using Maven (if installed):**
```bash
mvn spring-boot:run
```

**Method 3: Using IntelliJ IDEA:**
1. Open project in IntelliJ IDEA
2. Right-click on `DemoApplication.java`
3. Select "Run 'DemoApplication'"

### **Step 4: Verify Application is Running**

Look for this log message:
```
Started DemoApplication in X.XXX seconds
```

The application will be available at: `http://localhost:8080`

## 🧪 **Testing the API**

### **Method 1: Using curl (Command Line)**

```bash
# 1. Create a task
curl -X PUT "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!"
  }'

# 2. Get all tasks
curl -X GET "http://localhost:8080/tasks"

# 3. Get task by ID
curl -X GET "http://localhost:8080/tasks?id=123"

# 4. Execute task
curl -X PUT "http://localhost:8080/tasks/123/execute"

# 5. Search tasks
curl -X GET "http://localhost:8080/tasks/search?name=Hello"

# 6. Delete task
curl -X DELETE "http://localhost:8080/tasks/123"
```

### **Method 2: Using PowerShell (Windows)**

```powershell
# 1. Create a task
Invoke-WebRequest -Uri "http://localhost:8080/tasks" -Method PUT -ContentType "application/json" -Body '{"id": "123", "name": "Print Hello", "owner": "John Smith", "command": "echo Hello World!"}'

# 2. Get all tasks
Invoke-WebRequest -Uri "http://localhost:8080/tasks" -Method GET

# 3. Execute task
Invoke-WebRequest -Uri "http://localhost:8080/tasks/123/execute" -Method PUT
```

### **Method 3: Using Postman**

1. Import the `test-requests.http` file into Postman
2. Or create requests manually using the examples in `API_DOCUMENTATION.md`

### **Method 4: Using IntelliJ HTTP Client**

1. Open `test-requests.http` file in IntelliJ IDEA
2. Click the green arrow next to each request

## 🧪 **Running Tests**

```bash
# Run all tests
./mvnw test

# Run specific test
./mvnw test -Dtest=MongoDBConnectionTest

# Run with verbose output
./mvnw test -X
```

## 📋 **Git Commands**

### **Initial Setup:**
```bash
# Initialize Git repository
git init

# Add all files (excluding target/)
git add .

# Create .gitignore file
echo "target/" >> .gitignore
echo "*.class" >> .gitignore
echo ".idea/" >> .gitignore
echo "*.iml" >> .gitignore
echo ".DS_Store" >> .gitignore
echo "Thumbs.db" >> .gitignore

# Commit files
git commit -m "Initial commit: Task Management REST API"

# Add remote repository
git remote add origin <your-repository-url>

# Push to remote
git push -u origin main
```

### **Subsequent Updates:**
```bash
# Add changes
git add .

# Commit changes
git commit -m "Update: Description of changes"

# Push changes
git push
```

## 🔧 **Troubleshooting**

### **Common Issues:**

1. **MongoDB Connection Failed:**
   ```bash
   # Check if MongoDB is running
   netstat -an | findstr 27017
   
   # Start MongoDB with Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

2. **Java Version Issues:**
   ```bash
   # Check Java version
   java -version
   
   # Should show Java 21 or higher
   ```

3. **Maven Issues:**
   ```bash
   # Clean and rebuild
   ./mvnw clean compile
   
   # Or use Maven directly
   mvn clean compile
   ```

4. **Port Already in Use:**
   ```bash
   # Change port in application.properties
   server.port=8081
   ```

## 📁 **Complete File Structure for Git:**

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/kaiburr/demo/
│   │   │   ├── controller/TaskController.java
│   │   │   ├── DemoApplication.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── TaskNotFoundException.java
│   │   │   │   └── UnsafeCommandException.java
│   │   │   ├── model/
│   │   │   │   ├── Task.java
│   │   │   │   └── TaskExecution.java
│   │   │   ├── repository/TaskRepository.java
│   │   │   └── service/TaskService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/kaiburr/demo/
│           ├── DemoApplicationTests.java
│           ├── MongoDBConnectionTest.java
│           └── TaskControllerIntegrationTest.java
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
├── API_DOCUMENTATION.md
├── test-requests.http
├── sample_requests.json
└── .gitignore
```

## ✅ **Summary:**

1. **Upload**: All source code files, configuration files, and documentation
2. **Exclude**: `target/` directory, IDE files, OS files
3. **Run**: `./mvnw spring-boot:run` (after starting MongoDB)
4. **Test**: Use curl, Postman, or IntelliJ HTTP client
5. **Verify**: Check `http://localhost:8080` is accessible

Your application is ready to be uploaded to Git and run!
