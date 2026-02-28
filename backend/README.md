# Quiz Application Backend

A backend system for an online quiz platform, designed using Spring Boot with
a focus on clean architecture, strong domain modeling, and real-world backend workflows.

---

## 📌 Project Idea

The goal of this project is to build a scalable and maintainable backend
for conducting online quizzes. The system supports different user roles,
quiz creation and participation, and structured tracking of quiz attempts.

This project emphasizes backend design principles rather than UI development.

---

## 🎯 Objectives

- Design a clean domain model for a quiz system
- Separate business logic from controllers and persistence
- Handle real-world quiz workflows such as attempts and evaluation
- Build a backend that can later integrate with any frontend

---

## 🧩 Current Project Status

**Phase 1 – Backend Domain & Core Logic (Completed)**

- Core domain entities designed
- Entity relationships defined using JPA
- Service layer implemented with business logic
- Quiz attempt and evaluation flow implemented
- Centralized exception handling

Future phases will focus on REST APIs, security, and frontend integration.

---

## 🧠 Core Domain Model

The backend is centered around the following core entities:

- **Admin** – Creates and manages quizzes
- **Quiz** – Represents a quiz with metadata and configuration
- **Question** – Individual questions belonging to quizzes
- **Participant** – Users who attempt quizzes
- **QuizAttempt** – Tracks each participant’s attempt and result

---

## 🔗 High-Level Relationships

- An Admin can create multiple Quizzes
- A Quiz can contain multiple Questions
- A Participant can attempt multiple Quizzes
- Each attempt is stored and evaluated independently

---

## 🏗️ Project Structure (Simplified)

