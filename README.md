🚀 IntelliQuiz AI

AI-Powered Quiz & Assessment Platform

IntelliQuiz AI is a full-stack, role-based assessment platform that allows administrators to create, manage, and schedule quizzes while leveraging Artificial Intelligence to generate high-quality questions automatically.

The platform supports secure authentication, quiz lifecycle management, automated email notifications, and result publishing.

✨ Key Features
🔐 Role-Based Authentication

JWT-based authentication

Separate Admin and Participant roles

Secure login system

🧠 AI Question Generation

Automatically generate quiz questions using AI

Save generated questions directly into quizzes

Reduce manual effort for quiz creation

📝 Quiz Management (Admin)

Create quizzes (Draft mode)

Add / remove questions

Add / remove participants

Schedule quiz with start & end time

Publish results

Delete quiz (Draft only)

👨‍🎓 Participant Experience

View invited quizzes

Start quiz when LIVE

Submit answers

View results after publishing

⏳ Quiz Lifecycle
DRAFT → SCHEDULED → LIVE → CLOSED → RESULTS PUBLISHED

Questions & participants added in DRAFT

Scheduled by admin

Automatically transitions to LIVE

Auto-submission after end time

Results published by admin

📧 Email Integration

Participant credentials sent automatically

Quiz invitation emails

Quiz reminder emails

🏗️ Tech Stack
Backend

Java

Spring Boot

Spring Security

JWT Authentication

JPA / Hibernate

MySQL

Maven

Frontend

React

Axios

Modern UI components

AI Integration

AI-powered question generation service

📂 Project Structure
IntelliQuiz/
   backend/
       src/
       pom.xml
   frontend/
       src/
       package.json
   .gitignore
   README.md
⚙️ How to Run the Project
1️⃣ Backend
cd backend
mvn clean install
mvn spring-boot:run

Backend runs on:

http://localhost:8080
2️⃣ Frontend
cd frontend
npm install
npm start

Frontend runs on:

http://localhost:3000
🔒 Environment Configuration

Sensitive values should NOT be committed.

Configure in local environment:

Database credentials

Mail credentials

JWT secret key

🎯 Future Enhancements

Leaderboard & ranking system

Analytics dashboard

Detailed performance reports

Question difficulty levels

Multi-tenant support

📌 Why IntelliQuiz AI?

IntelliQuiz AI is designed to demonstrate:

Clean layered architecture

Secure authentication practices

State-based workflow management

Real-world email automation

AI service integration

Full-stack development skills

This project reflects strong backend fundamentals combined with modern frontend development and AI integration.

👨‍💻 Author

Vishwajeet Patil
Full-Stack Java Developer | AI Integration Enthusiast
