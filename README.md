# EagleBank API

A secure, modular Spring Boot 3.x REST API for user and bank account management, featuring JWT authentication, validation, and metrics.  
Supports H2 (in-memory, default) and MySQL (via profile) for persistence.  
Includes a React-based UI in `/eaglebank-ui`.

---

## Technologies

- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- H2 (default) / MySQL (optional)
- Maven
- Docker-ready
- Micrometer metrics
- React (UI, see `/eaglebank-ui`)

---

## Getting Started

### Prerequisites

- **Java 21**  
  [Download & install JDK 21](https://adoptium.net/temurin/releases/?version=21)
- **Maven**  
  [Download & install Maven](https://maven.apache.org/install.html)

---

### Run Locally (H2, default)

```bash
mvn spring-boot:run
```

- The API will start on [http://localhost:8080](http://localhost:8080)
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  (JDBC URL: `jdbc:h2:mem:eaglebank`)

---

### Run the React UI

```bash
cd eaglebank-ui
npm install
npm run dev
```
- UI will be available at [http://localhost:5173](http://localhost:5173)

---

## Authentication

- All protected endpoints require a JWT in the `Authorization: Bearer <token>` header.
- Obtain a JWT via `/api/auth/login` (see below).

---

## API Endpoints & Example Usage

### 1. **Register a User**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Example",
    "email": "alice@example.com",
    "phoneNumber": "+441234567890",
    "password": "password123",
    "address": {
      "line1": "123 Main St",
      "town": "London",
      "county": "Greater London",
      "postcode": "E1 6AN"
    }
  }'
```

---

### 2. **Login (Get JWT Token)**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "usr-abc123", "password": "password123"}'
```
- Response: `{ "jwt": "<token>" }`

---

### 3. **Get User Details**

```bash
curl -X GET http://localhost:8080/api/users/{userId} \
  -H "Authorization: Bearer <token>"
```

---

### 4. **Update User**

```bash
curl -X PUT http://localhost:8080/api/users/{userId} \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Updated",
    "email": "alice@newmail.com",
    "phoneNumber": "+441234567891",
    "password": "newpassword",
    "address": {
      "line1": "456 New St",
      "town": "London",
      "county": "Greater London",
      "postcode": "E1 7AN"
    }
  }'
```

---

### 5. **Delete User**

```bash
curl -X DELETE http://localhost:8080/api/users/{userId} \
  -H "Authorization: Bearer <token>"
```

---

### 6. **Create Bank Account**

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "Savings Account",
    "accountType": "SAVINGS"
  }'
```

---

### 7. **List User Accounts**

```bash
curl -X GET http://localhost:8080/api/accounts \
  -H "Authorization: Bearer <token>"
```

---

### 8. **Get Account Details**

```bash
curl -X GET http://localhost:8080/api/accounts/{accountNumber} \
  -H "Authorization: Bearer <token>"
```

---

### 9. **Update Account**

```bash
curl -X PUT http://localhost:8080/api/accounts/{accountNumber} \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "accountName": "Holiday Fund",
    "accountType": "PERSONAL"
  }'
```

---

### 10. **Delete Account**

```bash
curl -X DELETE http://localhost:8080/api/accounts/{accountNumber} \
  -H "Authorization: Bearer <token>"
```

---

## Testing

- Run all tests:
  ```bash
  mvn test
  ```

---

## Notes

- Default admin/test user may be created at startup (see `EagleBankApp.java`).
- All timestamps are stored in UTC.
- For more details, see Javadoc comments in the source code.

---

## Troubleshooting

- **Port in use?**  
  Change the port in `src/main/resources/application.properties`.
- **MySQL connection issues?**  
  Ensure Docker MySQL is running and credentials match your config.
- **JWT errors?**  
  Make sure you include the `Authorization: Bearer <token>` header.

---

## Project Structure

```
api/
  ├── src/main/java/com/eaglebank/api/
  ├── src/main/resources/
  ├── eaglebank-ui/           # React UI
  └── README.md
```

---