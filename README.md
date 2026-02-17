📌 Job Tracker System

A full-stack web application designed to streamline recruitment workflows by enabling HR users to manage job postings and candidates to track their job applications.

🚀 Live Demo

🔗 Application URL:
https://meticulous-gentleness-production.up.railway.app/

Overview

The Job Tracker System provides a structured platform for managing hiring workflows with secure role-based interactions.

The system allows:

HR users to create and manage job postings

Candidates to browse and apply for jobs

Role-based access control

Application status tracking

Automated email notifications

This project emphasizes backend architecture, security, and workflow-driven design.

Tech Stack
Backend

Java

Spring Boot

Spring MVC

Spring Security

Spring Data JPA

REST APIs

BCrypt Password Encryption

Frontend

HTML

CSS

JavaScript

Database

MySQL (or your DB)

Integrations

SendGrid (Email Notifications)

Key Features
Authentication & Security

Secure registration and login

Password encryption using BCrypt

Role-based authorization (HR / Candidate)

HR Functionalities

Create and manage job postings

View candidate applications

Update application statuses

Send automated email updates

Candidate Functionalities

Browse available jobs

Apply for job postings

Track application statuses

Workflow Management

Status-driven application tracking

User-specific data isolation

Validation and error handling

System Architecture

Layered architecture:

Controller → Service → Repository → Database


Ensures clean separation of concerns and maintainable design.

Project Structure
job-tracker-system/
│
├── src/main/java/com/jobtracker
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── config
│
├── src/main/resources
│   ├── static
│   └── application.properties
│
└── README.md

Database Schema
Users

id

username

password (BCrypt encrypted)

role

Jobs

id

title

description

requirements

Applications

id

job_id

user_id

status

Application Flow

Users register and authenticate securely

HR users create job postings

Candidates browse and apply

Applications stored via relational mapping

HR updates statuses

SendGrid triggers notifications

How to Run the Project
git clone https://github.com/bhavitha092005/job-tracker-system.git


Configure DB:

spring.datasource.url=jdbc:mysql://localhost:3306/job_tracker
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update


Run Spring Boot → Open browser.

Key Technical Concepts Implemented

Authentication & Authorization

Role-Based Access Control

RESTful API Design

Entity Relationship Mapping (JPA)

Workflow Logic

Email Integration

Future Enhancements

JWT-based authentication

Pagination and search

Advanced filtering

Microservices architecture

Cloud deployment

Author

Pala Bhavitha | 
Full Stack Developer

Focused on designing and developing full-stack web applications using Java, Spring Boot, and Spring Security. Actively advancing skills in React, Next.js, and microservices architecture.
