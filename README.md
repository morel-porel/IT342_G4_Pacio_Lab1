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

### Mobile (Android)
- **Language:** Kotlin
- **IDE:** Android Studio Iguana
- **Networking:** Retrofit 2 & Gson
- **Async:** Kotlin Coroutines
- **Storage:** SharedPreferences (Session Management)
- **UI:** XML Layouts & ViewBinding

### Database
- **MySQL 8.0+** (via XAMPP)

---

## Steps to Run Backend

### Prerequisites
- Java JDK 17 or higher
- IntelliJ IDEA (Recommended IDE) or VS Code with Java Extensions
- XAMPP (For the MySQL database)

### Setup Instructions

1. **Database Setup**
   1. Open XAMPP Control Panel and start Apache and MySQL.
   2. Open your browser and go to http://localhost/phpmyadmin.
   3. Click New in the sidebar.
   4. Enter database name: my_db (Must match application.properties).
   5. Click Create.

2. **Configure Backend**
   1. Navigate to backend/src/main/resources/.
   2. Open `backend/src/main/resources/application.properties`
   3. Ensure the settings match your XAMPP credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=[root or replace with username configured in MySQL]
   spring.datasource.password=[leave blank or replace with password configured in MySQL]
   spring.jpa.hibernate.ddl-auto=update

   ```

4. **Run Application via IntelliJ**
   1. Open IntelliJ IDEA.
   2. Select File > Open and select the backend folder.
   3. Wait for the project to index and download dependencies (look at the bottom right progress bar).
   4. Navigate to src/main/java/com/example/backend/BackendApplication.java.
   5. Look for the green Play/Run button next to public class BackendApplication (or inside the main method).
   6. Click Run 'BackendApplication'.

5. **Verify Server is Running**
   1. Look at the console logs. You should see:
   ```
   Tomcat started on port 8080 (http)
   Started BackendApplication in ... seconds
   ```
   2. Backend should be running on: `http://localhost:8080`

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

### Prerequisites
- Android Studio Iguana (or newer)
- Android Emulator (AVD) running API 24+
- Backend server running on `localhost:8080`

### Setup Instructions

1. **Open Project**
   - Open Android Studio.
   - Select `Open` and choose the `app` folder.

2. **Sync Gradle**
   - Allow Android Studio to download dependencies (Retrofit, Coroutines, etc.).

3. **Configure Emulator Connection**
   - The app uses `http://10.0.2.2:8080` to communicate with the local backend.
   - Ensure backend is running before launching the app.

4. **Run the App**
   - Select Emulator (e.g., Pixel 3a API 34).
   - Click the green **Run** (Play) button.

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
- **Error Response:**
  - **Code:** 409 CONFLICT
  - **Content:** `"Username or Email taken"`

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
  - **Content:** `"Invalid or Expired Token"`

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
