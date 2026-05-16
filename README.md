# Smart Management System (Full Stack)

A modern full-stack team task management application built with React, Spring Boot, PostgreSQL, JWT Authentication, and Docker.

The system allows teams to manage projects, assign tasks, track progress, and handle role-based access for Admins and Members.

--------------------------------------------------
🚀 LIVE DEMO
--------------------------------------------------

Frontend:
https://management-system-one-orcin.vercel.app

Backend API:
https://smart-management-system-05y5.onrender.com

--------------------------------------------------
📂 GITHUB REPOSITORY
--------------------------------------------------

https://github.com/rsingh241020/smart-management-system
https://github.com/rsingh241020/smart-management-system-frontend

--------------------------------------------------
✨ FEATURES
--------------------------------------------------

🔐 Authentication & Authorization
- User Signup/Login
- JWT Authentication
- Role-based access control
- Admin & Member roles

📁 Project Management
- Create projects
- Manage multiple projects
- Assign team members

✅ Task Management
- Create tasks
- Assign tasks to users
- Update task status
- Track overdue tasks
- Task summary dashboard

📊 Dashboard
- Total tasks
- Completed tasks
- Pending tasks
- Overdue tasks

--------------------------------------------------
🛠️ TECH STACK
--------------------------------------------------

Frontend:
- React
- Vite
- Tailwind CSS
- React Router

Backend:
- Spring Boot
- Spring Security
- JWT Authentication
- REST APIs

Database:
- PostgreSQL

Deployment:
- Docker
- Render
- Vercel

--------------------------------------------------
⚙️ ENVIRONMENT VARIABLES
--------------------------------------------------

Frontend (.env)

VITE_API_BASE_URL=https://smart-management-system-05y5.onrender.com


--------------------------------------------------
🐳 DOCKER SETUP
--------------------------------------------------

Dockerfile:

FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests=true

EXPOSE 8080

CMD ["java", "-jar", "target/Smart-Management-System-0.0.1-SNAPSHOT.jar"]

--------------------------------------------------
🔧 BACKEND SETUP
--------------------------------------------------

Run Backend:

./mvnw spring-boot:run

Windows:

.\mvnw.cmd spring-boot:run

--------------------------------------------------
💻 FRONTEND SETUP
--------------------------------------------------

Install Dependencies:

npm install

Start Frontend:

npm run dev

--------------------------------------------------
🔐 API ENDPOINTS
--------------------------------------------------

Authentication

POST   /api/users/register
POST   /api/users/login

Tasks

GET    /api/tasks
GET    /api/tasks/my
POST   /api/tasks
PATCH  /api/tasks/{id}/status

Projects

GET    /api/projects
POST   /api/projects

--------------------------------------------------
🌍 DEPLOYMENT
--------------------------------------------------

Frontend:
Vercel

Backend:
Render

Database:
PostgreSQL


--------------------------------------------------
👨‍💻 AUTHOR
--------------------------------------------------

Developed by Rohit Kumar
