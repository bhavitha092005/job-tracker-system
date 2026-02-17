📌 Job Tracker System

A secure full-stack web application designed to streamline job posting and candidate application workflows through authentication, role-based access control, and automated communication mechanisms.

🚀 Overview

The Job Tracker System enables structured interaction between HR users and Candidates, providing a centralized platform for job creation, application management, and status-driven workflows.

The application emphasizes security, clean architecture, and real-world system design using Spring Boot and modern web technologies.

✅ Key Features
🔐 Authentication & Security

Secure login and registration using Spring Security

BCrypt password encryption

Role-based access control (HR / Candidate)

Protected endpoints and user-specific data isolation

👩‍💼 HR Functionalities

Create and manage job postings

View candidate applications

Update application statuses

Trigger automated status notifications

👨‍💻 Candidate Functionalities

Secure account creation and login

Browse available job listings

Apply for jobs

Track application statuses

📧 Email Notification System

Integrated SendGrid Email API

Automated application status updates

Practical exposure to email notification workflows

📊 Workflow & Data Management

Status-driven application lifecycle

RESTful API-based communication

Persistent relational data modeling

Validation and error handling

🛠 Tech Stack
🔹 Backend

Java

Spring Boot

Spring Security

Spring MVC

Spring Data JPA

REST APIs

BCrypt Password Encryption

SendGrid Email Integration

MySQL Database

🔹 Frontend

HTML5

CSS3

JavaScript

🔹 Tools & Technologies

Git & GitHub

Postman

IntelliJ / STS / Eclipse

🏗 Architecture & Design

The application follows a layered architecture pattern:

Controller → Service → Repository → Database


✔ Separation of concerns
✔ Clean business logic handling
✔ Scalable backend structure
✔ RESTful API-driven interaction

🔐 Security Concepts Implemented

Authentication & Authorization

Role-Based Access Control (RBAC)

Password Encryption (BCrypt)

Endpoint Protection

User-Specific Data Isolation

🗄 Database Schema
Users Table

id

username

password (BCrypt encrypted)

role (HR / Candidate)

Jobs Table

id

job_title

company_name

description

created_by

Applications Table

id

job_id (foreign key)

user_id (foreign key)

status (APPLIED, INTERVIEW, OFFER, REJECTED)

🔄 Application Flow

Users register with credentials securely encrypted using BCrypt.

Spring Security handles authentication and authorization.

HR users create job postings via protected endpoints.

Candidates browse and apply for jobs.

Applications are stored with relational mappings.

HR updates application statuses.

SendGrid triggers automated email notifications.

🌐 API & Backend Highlights

RESTful API Design

Secure Endpoint Mapping

Business Logic Layer Separation

Entity Relationship Mapping (JPA)

Validation & Error Handling

🚀 Deployment

The application is deployed and accessible online.

🔗 Live Application:
https://https://meticulous-gentleness-production.up.railway.app/

🔗 GitHub Repository:
https://github.com/bhavitha092005/job-tracker-system

📸 Application Preview

screenshots/login.png  
screenshots/dashboard.png  
screenshots/job-posting.png

📚 Learning Outcomes

Designing RESTful APIs with Spring Boot

Implementing authentication using Spring Security

Applying secure password handling (BCrypt)

Role-based workflow design

Database modeling with JPA

Email service integration (SendGrid)

Debugging security and persistence workflows

🔮 Future Enhancements

JWT-based Stateless Authentication

Advanced Role Authorization Rules

Pagination & Filtering

Microservices-based Architecture

Cloud Deployment

👩‍💻 Author

Pala Bhavitha
Full Stack Developer

Focused on designing and developing full-stack web applications using Java, Spring Boot, and Spring Security. Actively advancing expertise in React, Next.js, and microservices architecture, with an emphasis on building scalable, secure, and production-ready systems.
