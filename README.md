# IT342 G4 Lab1 - User Authentication System

## Project Description

A full-stack user authentication system built with Spring Boot (backend), React (web frontend), and MySQL database. The application provides secure user registration, login, and profile management with JWT-based authentication.

---

## Technologies Used

### Backend
- **Java 17**
- **Spring Boot 4.0.2**
- **JWT (JSON Web Tokens)** - io.jsonwebtoken v0.11.5
- **MySQL Connector** - Database connectivity
- **Maven** - Dependency management

### Frontend (Web)
- **React** - UI framework
- **Vite** - Build tool and dev server
- **React Router DOM** - Routing and navigation
- **React Hook Form** - Form validation
- **Axios** - HTTP client for API requests
- **CSS3** - Styling

### Database
- **MySQL 8.0+** (via XAMPP)

### Mobile (In Progress)
- TBD

---

## Steps to Run Backend

### Prerequisites
- Java JDK 17 or higher
- Maven 3.6+
- MySQL (XAMPP recommended)

### Setup Instructions

1. **Start MySQL Server**
   ```bash
   # Start XAMPP and ensure MySQL is running
   # Default port: 3306
   ```

2. **Create Database**
   ```sql
   CREATE DATABASE my_db;
   ```

3. **Configure Database Connection**
   
   Edit `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```

4. **Navigate to Backend Directory**
   ```bash
   cd backend
   ```

5. **Build the Project**
   ```bash
   mvn clean install
   ```

6. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

   Or run the JAR file:
   ```bash
   java -jar target/backend-0.0.1-SNAPSHOT.jar
   ```

7. **Verify Server is Running**
   - Backend should be running on: `http://localhost:8080`
   - Check console for "Started BackendApplication" message

---

## Steps to Run Web App

### Prerequisites
- Node.js 16+ and npm/yarn
- Backend server must be running

### Setup Instructions

1. **Navigate to Web Directory**
   ```bash
   cd web
   ```

2. **Install Dependencies**
   ```bash
   npm install
   ```

3. **Verify API Configuration**
   
   Check `web/src/utils/api.js` has correct backend URL:
   ```javascript
   const api = axios.create({
       baseURL: 'http://localhost:8080/api',
   });
   ```

4. **Start Development Server**
   ```bash
   npm run dev
   ```

5. **Access the Application**
   - Open browser and navigate to: `http://localhost:5173`
   - You should see the login page

### Available Routes
- `/login` - User login page
- `/register` - New user registration
- `/dashboard` - Protected dashboard (requires authentication)

---

## Steps to Run Mobile App

> **Status:** In Development
> 
> Mobile application setup and run instructions will be added once development is complete.

---

## API Endpoints

### Base URL
```
http://localhost:8080
```

### Authentication Endpoints

#### 1. Register User
- **URL:** `/api/auth/register`
- **Method:** `POST`
- **Auth Required:** No
- **Request Body:**
  ```json
  {
    "username": "string",
    "email": "string",
    "firstname": "string",
    "lastname": "string",
    "password": "string"
  }
  ```
- **Success Response:**
  - **Code:** 200 OK
  - **Content:**
    ```json
    {
      "token": "jwt_token_string",
      "user": {
        "id": 1,
        "username": "string",
        "email": "string",
        "firstname": "string",
        "lastname": "string"
      }
    }
    ```

#### 2. Login User
- **URL:** `/api/auth/login`
- **Method:** `POST`
- **Auth Required:** No
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Success Response:**
  - **Code:** 200 OK
  - **Content:**
    ```json
    {
      "token": "jwt_token_string",
      "user": {
        "id": 1,
        "username": "string",
        "email": "string",
        "firstname": "string",
        "lastname": "string"
      }
    }
    ```
- **Error Response:**
  - **Code:** 401 UNAUTHORIZED
  - **Content:** `"Invalid credentials"`

#### 3. Get User Profile
- **URL:** `/api/auth/me`
- **Method:** `GET`
- **Auth Required:** Yes
- **Headers:**
  ```
  Authorization: Bearer <jwt_token>
  ```
- **Success Response:**
  - **Code:** 200 OK
  - **Content:**
    ```json
    {
      "id": 1,
      "username": "string",
      "email": "string",
      "firstname": "string",
      "lastname": "string"
    }
    ```
- **Error Response:**
  - **Code:** 401 UNAUTHORIZED
  - **Content:** `"Missing or invalid Authorization header"`

#### 4. Logout User
- **URL:** `/api/auth/logout`
- **Method:** `POST`
- **Auth Required:** Optional
- **Headers:**
  ```
  Authorization: Bearer <jwt_token> (optional)
  ```
- **Success Response:**
  - **Code:** 200 OK
  - **Content:** `"Logged out successfully"`
