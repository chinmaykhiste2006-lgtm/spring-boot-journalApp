# 📔 Journal App API

A Spring Boot REST API for managing journal entries with authentication, sentiment analysis, and starred entries.

---

## 🚀 Base URL

```
http://localhost:8081/journal
```

---

## ✨ Features

* 🔐 JWT Authentication
* 📔 Journal Entry Management
* ⭐ Starred Entries
* 👨‍💼 Admin Role Support
* 🌦️ Weather Integration
* 🧠 Sentiment Analysis

---

## ⚙️ Tech Stack

* Java + Spring Boot
* MongoDB
* Spring Security + JWT
* REST APIs

---

## ▶️ Run the Project

```bash
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8081/journal
```

---

## 🔐 Authentication

Use JWT token in headers:

```
Authorization: Bearer <your_token>
```

---

## 📌 API Endpoints

### Google OAuth API

| Method | Endpoint                | Description           |
| ------ | ----------------------  | --------------------- |
| GET    | `/auth/google/callback` | Google OAuth          |

### 🟢 Public APIs

| Method | Endpoint               | Description           |
| ------ | ---------------------- | --------------------- |
| GET    | `/public/health-check` | Check server          |
| POST   | `/public/signup`       | Register user         |
| POST   | `/public/login`        | Login & get JWT       |

---

### 👤 User APIs

| Method | Endpoint                 | Description      |
| ------ | ------------------------ | ---------------- |
| PUT    | `/user`                  | Update user      |
| DELETE | `/user`                  | Delete user      |
| GET    | `/user/greetings/{city}` | Greeting by city |

---

### 👨‍💼 Admin APIs

| Method | Endpoint                   | Description   |
| ------ | -------------------------- | ------------- |
| GET    | `/admin/all-users`         | Get all users |
| POST   | `/admin/create-admin-user` | Create admin  |

---

### 📔 Journal APIs

| Method | Endpoint           | Description     |
| ------ | ------------------ | --------------- |
| GET    | `/journal`         | Get all entries |
| POST   | `/journal`         | Create entry    |
| GET    | `/journal/id/{id}` | Get entry by ID |
| PUT    | `/journal/id/{id}` | Update entry    |
| DELETE | `/journal/id/{id}` | Delete entry    |

---

### ⭐ Starred APIs

| Method | Endpoint               | Description          |
| ------ | ---------------------- | -------------------- |
| GET    | `/star`                | Get starred entries  |
| POST   | `/star/add/id/{id}`    | Add starred entry    |
| DELETE | `/star/delete/id/{id}` | Remove starred entry |

---

## 📄 Swagger Documentation

After running the application, open:

```
http://localhost:8081/journal/swagger-ui.html
```

---

## 📦 Sample Requests

### 🔹 Sign Up

```json
POST /public/signup

{
  "username": "john_doe",
  "password": "secret123",
  "email": "john@example.com",
  "sentimentAnalysis": false
}
```

---

### 🔹 Login

```json
POST /public/login

{
  "username": "john_doe",
  "password": "secret123"
}
```

---

### 🔹 Create Journal Entry

```json
POST /journal

{
  "title": "My first journal entry",
  "content": "Today was a great day."
}
```

---

## ⚠️ Notes

* Use `Authorization: Bearer <JWT>` for protected APIs
* Use `{id}` for journal and starred entry operations
* Sentiment analysis works only if enabled for user

---

## 📁 Project Structure (Basic)

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── model/
 ├── config/
```

---

## 👨‍💻 Author

**Chinmay Khiste**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!
