# EagleBank JWT Login API

A Spring Boot 3.x application with modular design and secure JWT-based authentication.  
Supports H2 in-memory for testing and MySQL via profile.

---

## 🛠 Technologies
- Java 21
- Spring Boot 3.x
- Maven
- Spring Security + JWT
- H2 (default) / MySQL (optional)
- Docker-ready design

---

## 🚀 Getting Started

### 🔧 Prerequisites
- JDK 21
- Maven
- Docker (optional)

### 📦 Run Locally (H2)
```bash
mvn spring-boot:run

curl -X POST http://localhost:8080/v1/login \
-H "Content-Type: application/json" \
-d '{"username": "admin", "password": "password"}'

curl --verbose -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c3ItYXBrczd0ZkMiLCJpc3MiOiJlYWdsZWJhbmstYXV0aCIsImF1ZCI6ImVhZ2xlYmFuay1hcGkiLCJpYXQiOjE3NTI3ODQ2OTQsImV4cCI6MTc1Mjc4NDk5NH0.KPq98T1OfXBRjYE8oo1-sx2jzJL_81uHL_W9nDqAjaaDTPIASGeHrsTlxJIMhcPmWdqblvXPfgYrDiDxxy3zZb9IDQmlFXRMZ64Yb0aKpxQZlI-tOs-N1EE1930L-wzJxvR5Ir77ieTBwVxtnXg1TrM8UebKH4506_6Ln3gjstsovkxoLknuIICFOJR-nZb5yOS6LTDTbys_v0hdsWH6VGOz7Zrd_Gn2jqJGjiK7fxpknjbrkuOTasSkqw_kePjA6gwmIwO98Jgv6yiBPIF_FWzT0LEljhRl45tBw-xefUQZupU4ECCdWhGTgDk89PiaplW2lyw7y8mxYRdhEC81qA" http://localhost:8080/v1/users  

curl --verbose -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c3ItNDBvRHc4V2EiLCJpc3MiOiJlYWdsZWJhbmstYXV0aCIsImF1ZCI6ImVhZ2xlYmFuay1hcGkiLCJpYXQiOjE3NTI3ODUxMTQsImV4cCI6MTc1Mjc4NTQxNH0.agPmmLuZ66resB_lO2pCLOOekTbDQz026OTCy_0y1gK60HD8NGs-oofcYlO6MRItfH80aLwq3wZ4OlH3R5RaSGxxKHiRG5_Zr_qV7axOMezyNY-obWQKGdwkAEotmlyrnj-y8KYFAzOY0tFivj5yYquDmScD1blTL6DdwafKa3Dx9D4IYy8DjZDAK9GXbcDiQYSPBZD6Pl_TbvJOTmTdf5W8TuaPK527KQvBb60pb3xeOdxq1en1hdNVLAwXT8dYdawkGYBs7XHwBarWw9R3bizYUMXDLCdAN3wvDDGCaakoO2xGb1Kn4Zok1drt3KkrQifMiPDbkzjvyWm4FYZkOA" http://localhost:8080/v1/users/usr-40oDw8Wa
