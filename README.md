# Employee Management Application

A Spring Boot-based web application for managing employee data. The system allows you to add, update, delete, and view employees via both HTML views and RESTful APIs. It also includes integrated Swagger documentation for easy API testing.

How the application works:

Check it out in this link - https://drive.google.com/file/d/1EYr4rVNnXKf_7c12iI21RaptLMMkfD_9/view?usp=sharing

---

## 🔧 Tech Stack

- Java 11+
- Spring Boot
- Spring MVC & REST
- Spring Data JPA
- Thymeleaf
- Swagger (OpenAPI)
- Maven
- MongoDB

📁 Project File Structure
```bash

EmployeeManagementApplication/
├── EmpAppAPIDoc.json                     # Swagger/OpenAPI documentation
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/EmployeeManagementApplication/
│       │       ├── Config/
│       │       │   └── SwaggerConfig.java                 # Swagger setup
│       │       ├── Controller/
│       │       │   ├── EmployeeController.java            # Handles HTML views
│       │       │   └── EmployeeRestController.java        # RESTful API controller
│       │       ├── Entity/
│       │       │   └── EmployeeEntity.java                # JPA Entity for employee
│       │       ├── Exception/
│       │       │   ├── EmailAlreadyExistsException.java   # Custom exception
│       │       │   └── GlobalExceptionHandler.java        # Centralized exception handling
│       │       ├── Repository/
│       │       │   └── EmpRepository.java                 # JPA Repository
│       │       ├── Service/
│       │       │   ├── EmployeeService.java               # Service interface
│       │       │   └── EmpService.java                    # Service implementation
│       │       └── EmployeeManagementApplication.java     # Main Spring Boot class
│       └── resources/
│           ├── static/                                    # Static assets (empty now)
│           └── templates/                                 # Thymeleaf HTML views
│               ├── addEmployee.html
│               ├── index.html
│               ├── Update.html
│               └── view.html
├── .gitignore
├── pom.xml                                               # Maven configuration 
```

---

## ▶️ How to Run the Application

### 1. Clone the Repository

```bash
git clone https://github.com/sudhir1825/EmployeeManagementApplication.git
cd EmployeeManagementApplication
```

2. Build the Project
If you have Maven installed:
```bash

mvn clean install
```
Or use the Maven Wrapper:
```bash
./mvnw clean install        # macOS/Linux
mvnw.cmd clean install      # Windows
```
3. Run the Application
```bash
mvn spring-boot:run
```
Or with Maven Wrapper:
```bash
./mvnw spring-boot:run      # macOS/Linux
mvnw.cmd spring-boot:run    # Windows
```
🌐 Access Points
```bash
HTML UI: http://localhost:8081/
REST APIs:
GET /api/employees
POST /api/employees
PUT /api/employees/{id}
DELETE /api/employees/{id}
```
Swagger Documentation:
```bash
http://localhost:8081/swagger-ui.html
```
🧪 Sample API Payload (POST Employee)
```bash
{
  "Firstname": "Sudhir",
  "Lastname": "Rangasamy",
  "email": "sudhir@example.com", 
}
```
