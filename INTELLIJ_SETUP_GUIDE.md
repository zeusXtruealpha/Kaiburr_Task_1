# IntelliJ IDEA Setup Guide for Task Management API

## Prerequisites

### 1. Install MongoDB
**Option A: MongoDB Community Server**
1. Download from: https://www.mongodb.com/try/download/community
2. Install MongoDB Community Server
3. Start MongoDB service (usually starts automatically)

**Option B: Using Docker (Recommended)**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 2. Verify MongoDB is Running
```bash
# Check if MongoDB is running on port 27017
netstat -an | findstr 27017
```

## IntelliJ IDEA Setup

### Step 1: Open Project in IntelliJ IDEA

1. **Launch IntelliJ IDEA**
2. **File → Open** (or Ctrl+O)
3. **Navigate to**: `D:\Kaibur\demo`
4. **Select**: `pom.xml` file
5. **Click**: "Open as Project"
6. **Wait**: For Maven import to complete (bottom right progress bar)

### Step 2: Configure Project Settings

1. **File → Project Structure** (Ctrl+Alt+Shift+S)
2. **Project Settings → Project**:
   - **Project SDK**: Java 21
   - **Project language level**: 21
3. **Project Settings → Modules**:
   - Ensure `demo` module is selected
   - **Language level**: 21
4. **Click**: "Apply" and "OK"

### Step 3: Install Required Plugins

1. **File → Settings → Plugins** (Ctrl+Alt+S)
2. **Search and install**:
   - Spring Boot (if not already installed)
   - Maven Helper (optional, for dependency management)
3. **Restart IntelliJ** if prompted

### Step 4: Configure Run Configuration

1. **Run → Edit Configurations**
2. **Click**: "+" → "Spring Boot"
3. **Configure**:
   - **Name**: Task Management API
   - **Main class**: `com.kaiburr.demo.DemoApplication`
   - **Module**: demo
   - **JRE**: Java 21
4. **Click**: "Apply" and "OK"

## Running the Application

### Method 1: Using Run Configuration (Recommended)

1. **Select**: "Task Management API" from the run configuration dropdown
2. **Click**: Green "Run" button (▶️)
3. **Or press**: Shift+F10

### Method 2: Using Maven

1. **Open Maven tool window**: View → Tool Windows → Maven
2. **Expand**: demo → Plugins → spring-boot
3. **Double-click**: "spring-boot:run"

### Method 3: Using Terminal

1. **Open Terminal**: View → Tool Windows → Terminal
2. **Run**: `mvn spring-boot:run`

### Method 4: Right-click on Main Class

1. **Navigate to**: `src/main/java/com/kaiburr/demo/DemoApplication.java`
2. **Right-click** on the class
3. **Select**: "Run 'DemoApplication'"

## Verifying the Application

### 1. Check Application Startup
Look for these logs in the console:
```
Started DemoApplication in X.XXX seconds
```

### 2. Test API Endpoints

**Using IntelliJ HTTP Client:**
1. **Create new file**: `test-requests.http` in project root
2. **Add the following content**:

```http
### Create a task
PUT http://localhost:8080/tasks
Content-Type: application/json

{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!"
}

### Get all tasks
GET http://localhost:8080/tasks

### Execute task
PUT http://localhost:8080/tasks/123/execute

### Search tasks
GET http://localhost:8080/tasks/search?name=Hello

### Delete task
DELETE http://localhost:8080/tasks/123
```

3. **Click**: Green arrow next to each request to test

**Using PowerShell/Command Line:**
```powershell
# Create a task
Invoke-WebRequest -Uri "http://localhost:8080/tasks" -Method PUT -ContentType "application/json" -Body '{"id": "123", "name": "Print Hello", "owner": "John Smith", "command": "echo Hello World!"}'

# Get all tasks
Invoke-WebRequest -Uri "http://localhost:8080/tasks" -Method GET

# Execute task
Invoke-WebRequest -Uri "http://localhost:8080/tasks/123/execute" -Method PUT
```

## MongoDB Verification

### 1. Check MongoDB Connection
The application will show MongoDB connection logs:
```
Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, cryptd=false, state=CONNECTED, ok=true}
```

### 2. View Data in MongoDB
**Using MongoDB Compass:**
1. **Download**: MongoDB Compass from https://www.mongodb.com/products/compass
2. **Connect to**: `mongodb://localhost:27017`
3. **Navigate to**: `taskdb` database
4. **View collections**: `tasks` and `taskExecutions`

**Using MongoDB Shell:**
```bash
# Connect to MongoDB
mongo

# Use the database
use taskdb

# View tasks
db.tasks.find()

# View task executions
db.taskExecutions.find()
```

## Troubleshooting

### Common Issues:

1. **MongoDB Connection Failed**:
   - Ensure MongoDB is running: `netstat -an | findstr 27017`
   - Check MongoDB service status
   - Verify connection string in `application.properties`

2. **Java Version Issues**:
   - Ensure Java 21 is installed and configured
   - Check Project SDK in Project Structure

3. **Maven Issues**:
   - Refresh Maven project: Right-click on `pom.xml` → Maven → Reload project
   - Clean and rebuild: Maven tool window → Lifecycle → clean → compile

4. **Port Already in Use**:
   - Change port in `application.properties`: `server.port=8081`
   - Or kill process using port 8080

### Debug Mode:
1. **Run → Edit Configurations**
2. **Add VM options**: `-Dspring.profiles.active=debug`
3. **Or add to application.properties**: `logging.level.com.kaiburr.demo=DEBUG`

## Project Structure in IntelliJ

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/kaiburr/demo/
│   │   │   ├── controller/          # REST API endpoints
│   │   │   ├── model/               # Data models
│   │   │   ├── repository/           # MongoDB repositories
│   │   │   ├── service/             # Business logic
│   │   │   ├── exception/           # Custom exceptions
│   │   │   └── DemoApplication.java # Main class
│   │   └── resources/
│   │       └── application.properties # Configuration
│   └── test/                        # Test files
├── pom.xml                          # Maven configuration
└── README.md                        # Documentation
```

## Success Indicators

✅ **Application starts without errors**  
✅ **MongoDB connection established**  
✅ **API endpoints respond correctly**  
✅ **Tasks are stored in MongoDB**  
✅ **Command execution works**  
✅ **All tests pass**

## Next Steps

1. **Run the application** using any of the methods above
2. **Test the API** using the provided examples
3. **Check MongoDB** to see stored data
4. **Run tests** to verify everything works
5. **Explore the code** to understand the implementation

The application is now ready to use with full MongoDB integration!
