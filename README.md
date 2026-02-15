# Job Tracker System

A full-stack Job Tracking System built using **Java, Spring Boot, Spring Security, REST APIs, MySQL**, and a **vanilla HTML/CSS/JavaScript frontend**.  
The application supports **role-based authentication** using **JWT** and demonstrates real-world backend and frontend integration.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Secure login & registration
- Role-based access control using Spring Security

### 👥 Roles
- **ADMIN**
  - Create HR users
- **HR**
  - Create job postings
  - View applications for their jobs
  - Update application status
  - Download candidate resumes
- **CANDIDATE**
  - Register & login
  - View available jobs
  - Apply for jobs with resume upload
  - Track application status

### 📄 Job Application Workflow
1. Admin creates HR
2. HR creates job postings
3. Candidate applies with resume (PDF)
4. HR reviews applications
5. HR updates application status
6. Candidate sees real-time status updates

---

## 🛠 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- RESTful APIs
- JWT Authentication
- MySQL Database
- Maven

### Frontend
- HTML
- CSS (custom, no framework)
- JavaScript (Vanilla JS)
- Fetch API

### Database
- MySQL
- JPA/Hibernate ORM

---

## 🗂 Project Structure

src/main/java/com/jobtracking
│
├── config # Security, CORS, File storage config
├── controller # REST Controllers
├── dto # Request/Response DTOs
├── entity # JPA Entities
├── enums # Enums (Roles, Status)
├── exception # Custom exceptions & handlers
├── repository # JPA Repositories
├── security # JWT, Filters, UserDetails
├── service # Business logic
└── JobTrackerSystemApplication.java

src/main/resources/static
│
├── index.html
├── register.html
├── admin.html
├── hr.html
├── candidate.html
├── css
│ └── style.css
└── js
├── auth.js
├── admin.js
├── hr.js
├── candidate.js
└── register.js


---

## 🔑 Security Implementation

- Stateless authentication using JWT
- Custom `JwtAuthenticationFilter`
- Role-based endpoint protection
- Password hashing using BCrypt
- Method-level security using `@PreAuthorize`

---

## 📦 API Highlights

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### Admin
- `POST /api/admin/create-hr`

### HR
- `POST /api/jobs`
- `GET /api/applications/job/{jobId}`
- `PATCH /api/applications/{id}/status`
- `GET /api/applications/resume/{id}`

### Candidate
- `GET /api/jobs`
- `POST /api/applications/apply`
- `GET /api/applications/me`

---

## 📁 Resume Upload
- Accepts **PDF only**
- Max size: **2MB**
- Stored securely on server
- Download access restricted to HR who owns the job

---

## ⚙️ How to Run Locally

### 1️⃣ Clone Repository
```bash
git clone <your-repo-url>
cd job-tracker-system
