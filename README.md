# Secure Healthcare Management CRUD API

The **Secure Healthcare Management API** is a **Spring Boot–based backend application** for managing **Users, Patients, Medical Records, and Prescriptions** with **Role-Based Access Control (RBAC)**.

It provides **secure authentication & authorization using JWT**, while ensuring **audit logging** for key healthcare actions. The project integrates **multi-database support (PostgreSQL + H2)** and enforces **HTTPS/TLS** for secure communication.

This project demonstrates **enterprise-grade application design** with a strong focus on **security, scalability, and maintainability**.

---

## Features

### Authentication & Authorization

- **JWT-based Authentication (Stateless)** – Issue JWT tokens containing user roles & permissions.

- **Role-Based Access Control (RBAC)** – Four roles with specific permissions:

    - **Patient** → View Own Medical Records.

    - **Nurse** → View Medical Records,   Update Patient Status
      
    - **Doctor** → View Medical Records, Update Patient Status, Prescribe Medication

    - **Administrator** → Manage Users, Create Medical Records

- **Password Encryption** – User passwords secured using **BCryptPasswordEncoder.**

### Medical Records Management API (CRUD)

- **GET /api/records/my** – Patients: View only their own medical records.

- **GET /api/records/{patientId}** - Doctors/Nurses: View medical records of a specific patient by ID.

- **GET /api/records** – Doctors/Nurses: View all medical records.

- **PATCH /api/records/{patientId}/status** – Doctors/Nurses: Update the admission/discharge status of a patient.

- **POST /api/records/{patientId}/prescriptions** – Doctors: Prescribe medication for a specific patient.

- **Medical Record attributes**: id, patientId, data, status, createdAt.

### User Management

- **POST /api/users** – Admin can create new users with roles (Patient, Nurse, Doctor, Administrator).

- **POST /register** – Patients can self-register.

- **POST /auth/login** - Authenticates a user and issues a JWT token based on their role (Patient, Nurse, Doctor, Administrator).

- **Role enforcement** on all APIs.

### Audit Logging

- Every action is tracked:
```
          CREATED_USER, CREATED_RECORD, PRESCRIBED_MEDICATION, UPDATED_PATIENT_STATUS
```

- **Audit Log Schema**: id, userId, action, patientId, timestamp.

- Stored in **Postgres** for durability.

### Security

- **HTTPS Enforcement** – Redirect HTTP → HTTPS automatically.

- **TLS/SSL Certificates** for secure communication.

- **Audit Logging** for all user actions.

- **JWT Authentication & Role-Based Access Control (RBAC).**

### Database Setup

- **H2 (In-memory)** → Medical Records Data.

- **Postgres** → Users & Audit Logs.

- **data.sql** → Preloads sample medical records for testing.

## Tech Stack

- **Java** 

- **Spring Boot / Spring Framework**

- **Spring Web (REST API)**

- **Spring Security (JWT + RBAC)**

- **Spring Data JPA** (H2 + PostgreSQL)

- **Maven** (Dependency Management)

- **Postman** (API Testing)

- **Git** (Version Control)

## Project Setup

#### Clone the Repository

```
git clone https://github.com/your-username/Healthcare-Secure-API-SpringBoot-MultiDB-JWT-RBAC.git
cd Healthcare-Secure-API-SpringBoot-MultiDB-JWT-RBAC
```

#### Configure Databases

- **Postgres**: Create a database healthcare_db.

- Update application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/healthcare_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
- **H2**: Auto-configured for Medical Records data (no setup needed).

#### Generate Keystore for HTTPS
```
keytool -genkeypair -alias mycert -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass changeit

```

- Place keystore.p12 in src/main/resources.

- Update application.properties:
```
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_password
server.ssl.key-store-type=PKCS12
server.port=8443
```

#### Generate Certificates for Postman Testing

- Extract public certificate:
```
keytool -exportcert -rfc -alias mycert -keystore keystore.p12 -storepass changeit -file mycert.pem

```

- import **mycert.pem** in Postman:

    - Go to **Settings → Certificates → CA Certificates**

    - Upload mycert.pem.

 #### Enable Postman Settings

- Go to **Postman Settings → General.**

- Enable **Follow Original HTTP Method.**

#### Run the Project
```
mvn spring-boot:run
```
Server runs at: https://localhost:8443

## API Testing (Postman)
### Authentication

- **Login Endpoint** -> POST /auth/login

Request
```
{
  "username": "admin",
  "password": "admin123"
}
```

Response
```
{
  "access_token": "jwt_token",
  "expires_in": 3600
}
```

Use **Authorization: Bearer <token>** in all secured requests.

**Note**: Any role (Patient, Nurse, Doctor, Administrator) can login via /auth/login and receive a valid JWT token. The **role determines access** to other endpoints.

## Default Data

- Preloaded via data.sql.

- Example Product:
  
```
INSERT INTO records (patientId, status, createdAt) VALUES
  (1, 'Admitted', CURRENT_TIMESTAMP);
```

## Audit Logs

- Stored in Postgres.

- Example record:
```
{
 "id": 1,
  "action": "CREATED_RECORD",
  "patient_id": 10,
  "timestamp": "2025-08-20T10:15:30"
}
```

## Highlights

- Multi-Database Integration (**H2 + PostgreSQL**)
- Secure API with **HTTPS + TLS**
- **JWT Authentication**
- Role-Based Access Control (RBAC)
- **BCrypt Password Hashing**
- Default data loading with data.sql
- Comprehensive **Audit Logging**
- **Postman Ready** with HTTPS certificate setup

## Why this project?

This project demonstrates proficiency in:

- **Spring Framework (Core, Boot, Web, Security, Data JPA)**

- **Enterprise Security (JWT, RBAC, HTTPS, BCrypt)**

- **Database Management (H2 for dev/test, PostgreSQL for persistence)**

- **REST API Design & Documentation**

- **Testing & API Tooling (Postman)**

- **Best Practices (Audit Logs, HTTPS, Data Preloading)**

This project reflects real-world challenges in **enterprise application development** and proves capability to handle them effectively.

### Author

**Prem Swaroop** – Java Developer passionate about **secure, scalable, and maintainable enterprise solutions.**

### License

MIT License – feel free to use, learn, and extend.
