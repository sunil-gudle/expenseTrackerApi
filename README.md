# Expense Tracking API

A secure, robust RESTful API built with **Spring Boot** and **Java 17** for managing personal expenses. This application allows users to register, log in, and track their expenses with full CRUD support, pagination, search, and filtering options.

---

## 🚀 Features

- **User Authentication & Authorization**: 
  - User registration and login using Spring Security.
  - Password hashing using BCrypt.
  - Role-based basic authentication for securing REST endpoints.
- **User Management**:
  - Fetch, update, and delete user profiles.
- **Expense Tracking**:
  - Add, view, update, and delete expense entries.
  - Filter expenses by category, date range, or search keyword.
  - Fully paginated response structures for all listing endpoints.
- **Robust Exception Handling**:
  - Global error responses with descriptive messages and status codes.
- **Database Association**:
  - Cascade delete for user profiles to automatically clean up all associated expenses.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.3.x
- **Security**: Spring Security (HTTP Basic Auth, BCrypt)
- **Database**: MySQL, Spring Data JPA (Hibernate)
- **Build Tool**: Gradle
- **Utilities**: Lombok, Spring Validation

---

## 📋 Prerequisites

Before running the application, make sure you have the following installed:
- **Java JDK 17** or higher
- **MySQL Database**
- **Gradle** (optional, wrapper is provided)

---

## ⚙️ Configuration & Setup

### 1. Database Setup
Create a new MySQL database named `expensetracker`:
```sql
CREATE DATABASE expensetracker;
```

### 2. Application Configuration
Configure your database details in `src/main/resources/application.yml`. Replace the credentials with your own details:

```yaml
spring:
  application:
    name: expenseTrackerApi
  datasource:
    url: jdbc:mysql://localhost:3306/expensetracker
    username: YOUR_DATABASE_USERNAME
    password: YOUR_DATABASE_PASSWORD
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect

server:
  servlet:
    context-path: /api/v1
```

---

## 🏃 Running the Application

To start the application locally, run:

```bash
./gradlew bootRun
```

The server will start at `http://localhost:8080/api/v1`.

---

## 🔑 API Endpoints

### 1. Authentication & Registration
These endpoints do not require authentication:

| HTTP Method | Endpoint | Description | Request Body Example |
|---|---|---|---|
| `POST` | `/register` | Register a new user | See User Register payload |
| `POST` | `/login` | Authenticate / Log in a user | See User Login payload |

#### User Register Request Payload (`POST /register`)
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "securepassword",
  "age": 28
}
```

#### User Login Request Payload (`POST /login`)
```json
{
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

---

### 2. User Management
*Requires Basic Authentication*

| HTTP Method | Endpoint | Description |
|---|---|---|
| `GET` | `/users/{id}` | Retrieve details of a user by ID |
| `PUT` | `/users/{id}` | Update details of a user by ID |
| `DELETE` | `/users/{id}` | Delete user profile (also deletes associated expenses) |

---

### 3. Expense Management
*Requires Basic Authentication*

| HTTP Method | Endpoint | Description | Query Parameters / Details |
|---|---|---|---|
| `GET` | `/expenses` | List all expenses of the logged-in user | Supports `page`, `size`, `sort` (e.g., `?page=0&size=5&sort=amount,desc`) |
| `GET` | `/expenses/{id}` | Retrieve details of a specific expense | |
| `POST` | `/expenses` | Create a new expense | See Expense payload |
| `PUT` | `/expenses/{id}` | Update an existing expense | See Expense payload |
| `DELETE` | `/expenses` | Delete an expense by ID | Requires query param: `?id=1` |
| `GET` | `/expenses/category` | Filter expenses by category | Requires query param: `?category=Food` |
| `GET` | `/expenses/name` | Search expenses by name/keyword | Requires query param: `?keyword=lunch` |
| `GET` | `/expenses/date` | Filter expenses by date range | Optional query params: `?startDate=2026-01-01&endDate=2026-12-31` |

#### Expense Creation Request Payload (`POST /expenses`)
```json
{
  "name": "Grocery Shopping",
  "description": "Weekly grocery purchase from supermarket",
  "amount": 150.75,
  "category": "Food",
  "date": "2026-05-24"
}
```

---

## 🛡️ Security Details
- The endpoints are secured using Spring Security's HTTP Basic Authentication.
- When calling secured endpoints (e.g., `/expenses` or `/users/{id}`), you must provide the **email** as the username and your **password** via HTTP Basic Authentication headers.
- Passwords stored in `tbl_users` are encrypted using the BCrypt hashing function.

---

## 🚫 Error Handling
The API handles various runtime scenarios and returns clean, uniform JSON error structures with the following fields:
- `statusCode`: HTTP Status Code (e.g., `404`, `409`, `400`)
- `message`: Contextual error message
- `timestamp`: Time of the error

For example, when a resource is not found:
```json
{
  "statusCode": 404,
  "message": "Expense is not found for the id 99",
  "timestamp": "2026-05-24T00:51:49.000+00:00"
}
```
