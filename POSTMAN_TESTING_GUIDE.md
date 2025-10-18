# Postman API Testing Guide

## 🚀 **Setup Instructions**

### **1. Start MongoDB:**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### **2. Start Application:**
```bash
# Use Maven wrapper (recommended)
./mvnw spring-boot:run

# Or if Maven is installed
mvn spring-boot:run
```

### **3. Verify Application:**
- Application runs on: `http://localhost:8080`
- Look for: `Started DemoApplication in X.XXX seconds`

---

## 📋 **Postman Collection - All Endpoints**

### **Base URL:** `http://localhost:8080`

---

## **1. CREATE TASK** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!"
  }
  ```

### **Expected Response (200 OK):**
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": []
}
```

---

## **2. CREATE ANOTHER TASK** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "id": "456",
    "name": "List Directory",
    "owner": "Jane Doe",
    "command": "dir"
  }
  ```

### **Expected Response (200 OK):**
```json
{
  "id": "456",
  "name": "List Directory",
  "owner": "Jane Doe",
  "command": "dir",
  "taskExecutions": []
}
```

---

## **3. GET ALL TASKS** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
[
  {
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!",
    "taskExecutions": []
  },
  {
    "id": "456",
    "name": "List Directory",
    "owner": "Jane Doe",
    "command": "dir",
    "taskExecutions": []
  }
]
```

---

## **4. GET TASK BY ID** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks?id=123`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": []
}
```

---

## **5. GET NON-EXISTENT TASK** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks?id=999`
- **Headers:** None required

### **Expected Response (404 Not Found):**
```json
{
  "error": "Task not found",
  "message": "Task not found with id: 999"
}
```

---

## **6. SEARCH TASKS BY NAME** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks/search?name=Hello`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
[
  {
    "id": "123",
    "name": "Print Hello",
    "owner": "John Smith",
    "command": "echo Hello World!",
    "taskExecutions": []
  }
]
```

---

## **7. SEARCH TASKS BY NAME (No Results)** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks/search?name=NonExistent`
- **Headers:** None required

### **Expected Response (404 Not Found):**
```json
{
  "error": "Task not found",
  "message": "No tasks found with name containing: NonExistent"
}
```

---

## **8. EXECUTE TASK** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks/123/execute`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
{
  "id": null,
  "startTime": "2025-10-18 23:30:15.123Z",
  "endTime": "2025-10-18 23:30:15.456Z",
  "output": "Hello World!\n"
}
```

---

## **9. EXECUTE ANOTHER TASK** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks/456/execute`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
{
  "id": null,
  "startTime": "2025-10-18 23:30:20.789Z",
  "endTime": "2025-10-18 23:30:20.890Z",
  "output": "Volume in drive C is Windows\nVolume Serial Number is XXXX-XXXX\n\nDirectory of C:\\Users\\...\n\n[DIR]  .\n[DIR]  ..\n..."
}
```

---

## **10. GET TASK WITH EXECUTION HISTORY** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks?id=123`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World!",
  "taskExecutions": [
    {
      "id": null,
      "startTime": "2025-10-18 23:30:15.123Z",
      "endTime": "2025-10-18 23:30:15.456Z",
      "output": "Hello World!\n"
    }
  ]
}
```

---

## **11. TEST UNSAFE COMMAND** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "id": "999",
    "name": "Dangerous Task",
    "owner": "Hacker",
    "command": "rm -rf /"
  }
  ```

### **Expected Response (400 Bad Request):**
```json
{
  "error": "Unsafe command",
  "message": "Command contains unsafe operations. Please use safe commands only."
}
```

---

## **12. TEST MISSING REQUIRED FIELDS** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks`
- **Headers:**
  ```
  Content-Type: application/json
  ```
- **Body (raw JSON):**
  ```json
  {
    "id": "",
    "name": "Test Task",
    "owner": "Test User",
    "command": "echo test"
  }
  ```

### **Expected Response (400 Bad Request):**
```json
"Error: Task ID is required"
```

---

## **13. EXECUTE NON-EXISTENT TASK** ✅

### **Request:**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/tasks/999/execute`
- **Headers:** None required

### **Expected Response (404 Not Found):**
```json
{
  "error": "Task not found",
  "message": "Task not found with id: 999"
}
```

---

## **14. DELETE TASK** ✅

### **Request:**
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/tasks/123`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
"Task deleted successfully"
```

---

## **15. DELETE NON-EXISTENT TASK** ✅

### **Request:**
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/tasks/999`
- **Headers:** None required

### **Expected Response (404 Not Found):**
```json
{
  "error": "Task not found",
  "message": "Task not found with id: 999"
}
```

---

## **16. VERIFY DELETION** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks?id=123`
- **Headers:** None required

### **Expected Response (404 Not Found):**
```json
{
  "error": "Task not found",
  "message": "Task not found with id: 123"
}
```

---

## **17. GET REMAINING TASKS** ✅

### **Request:**
- **Method:** `GET`
- **URL:** `http://localhost:8080/tasks`
- **Headers:** None required

### **Expected Response (200 OK):**
```json
[
  {
    "id": "456",
    "name": "List Directory",
    "owner": "Jane Doe",
    "command": "dir",
    "taskExecutions": [
      {
        "id": null,
        "startTime": "2025-10-18 23:30:20.789Z",
        "endTime": "2025-10-18 23:30:20.890Z",
        "output": "Volume in drive C is Windows\n..."
      }
    ]
  }
]
```

---

## 🧪 **Testing Sequence**

### **Complete Test Flow:**
1. **Create Task 1** → Should return 200 OK
2. **Create Task 2** → Should return 200 OK
3. **Get All Tasks** → Should return array with 2 tasks
4. **Get Task by ID** → Should return specific task
5. **Search Tasks** → Should return matching tasks
6. **Execute Task 1** → Should return execution result
7. **Execute Task 2** → Should return execution result
8. **Get Task with History** → Should show execution history
9. **Test Unsafe Command** → Should return 400 Bad Request
10. **Test Missing Fields** → Should return 400 Bad Request
11. **Execute Non-existent** → Should return 404 Not Found
12. **Delete Task** → Should return 200 OK
13. **Delete Non-existent** → Should return 404 Not Found
14. **Verify Deletion** → Should return 404 Not Found
15. **Get Remaining Tasks** → Should return remaining tasks

---

## 📱 **Postman Collection Import**

### **Create Postman Collection:**
1. Open Postman
2. Click "Import"
3. Create new collection: "Task Management API"
4. Add all the requests above
5. Save collection

### **Environment Variables:**
Create environment with:
- `base_url`: `http://localhost:8080`

Then use `{{base_url}}/tasks` in requests.

---

## ✅ **Expected Results Summary:**

- **✅ All CRUD operations working**
- **✅ Command execution working**
- **✅ Security validation working**
- **✅ Error handling working**
- **✅ MongoDB integration working**
- **✅ Task execution history working**

Your API is fully functional and ready for testing!
