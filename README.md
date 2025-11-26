# 🧠 Smart Task Manager Backend

A secure, production-grade backend for managing tasks with role-based access, JWT authentication, and audit tracking — built with **Spring Boot** and **MySQL**.

## 🚀 Project Overview
This backend powers a task management platform where users can:
- Register, login, and manage personal tasks
- Filter, sort, and paginate task lists
- Update their profile securely
- Admins can assign roles, activate/deactivate users, and view users by role

Built with industry best practices — validation, exception handling, audit logging, and OpenAPI documentation.

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security + JWT
- MySQL
- Spring Data JPA
- Swagger / OpenAPI

## ✨ Features
- JWT-based authentication & user registration
- Role-based access control (USER, ADMIN)
- Task CRUD with pagination, sorting, filtering
- Audit fields (createdAt, updatedAt)
- Admin features: role assignment, user activation, user listing
- Global exception handling
- Swagger UI 

## ⚙️ Setup Instructions
### Clone repo
git clone https://github.com/shubh81037/smart-task-manager-backend.git
cd smart-task-manager-backend

### Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
jwt.secret=your_jwt_secret_key

### Run
mvn spring-boot:run

## 🧪For Testing -->
👤 Admin Role: ROLE_ADMIN  
📧 Email: admin@example.com  
🔐 Password: admin123


## 📄 API Docs
Swagger: http://localhost:8082/swagger-ui/index.html
OpenAPI: http://localhost:8082/v3/api-docs

## 🚀 Deployment Guide (Local Deployment)
- Ensure MySQL is running
- Update application.properties or environment variables
- Run via Maven or IDE
- Access backend at: http://localhost:8082

## 📁 Folder Structure
src/
├── main/
│   ├── java/com/example/taskManager/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── repository/
│   │   ├── security/
│   │   └── utility/
│   └── resources/
│       └── application.properties

## 👨‍💻 Author
Shubham — Aspiring backend developer focused on secure, scalable systems.
🔗 [GitHub](https://github.com/shubh81037)


    