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

curl --verbose -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c3ItQ3ZCaHBrMkkiLCJpc3MiOiJlYWdsZWJhbmstYXV0aCIsImF1ZCI6ImVhZ2xlYmFuay1hcGkiLCJpYXQiOjE3NTMxMTAxNjIsImV4cCI6MTc1MzExMDQ2Mn0.NizWT9YuJ7KFz_paMf3Myvc3FEs_GFV-LQMVidhl76iJYG0r3aRUe0LXSUhdz_ymiEWzk0hXMi2hYLw1vII63pD69HfnHTmQ_jPS4FFlvsiTGBwEUFu344kAD-mpw6fxAkRp2aeRRUNqYxqx8iEWFHrQ9HJqL530Sze1FMnL-lIcseMdu1QrNNxLT0St2YsaMDcnaypI7rey9Ur-2qcJkRSVwedSGa9cShQKC8F4FhPHmeYkLy8SApmBbv4xyaVvsUZdMwUV-VLXA9PK94kz99YUghh_S7P6_gXM239MyF_EX9UeAmTiuiCCYE2xL5IVuhgXwtLEjiaQGa4pL-KOpg" http://localhost:8080/v1/users/usr-CvBhpk2I
