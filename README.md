# Conecta Cabo Verde - Backend

Backend API for a local business discovery platform.

## 🚀 Features

- User registration and login
- JWT authentication
- Role-based authorization (ADMIN / BUSINESS_OWNER)
- Business creation and management
- Ownership-based access control
- Admin approval system (PENDING → APPROVED / REJECTED)
- Global exception handling
- Automatic timestamps

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Maven

## 🔐 Security

- JWT authentication
- Role-based access control
- Business ownership enforcement

## 📦 API Examples

- POST /api/users → Register
- POST /api/users/login → Login
- POST /api/businesses → Create business
- GET /api/businesses/my → Get my businesses
- PUT /api/businesses/{id} → Update business
- PUT /api/businesses/{id}/approve → Admin approve
- PUT /api/businesses/{id}/reject → Admin reject

## 📌 Status

Backend MVP complete. Frontend coming next.
