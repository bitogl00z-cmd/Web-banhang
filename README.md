# FashionStyle - Thời trang cho mọi phong cách

Spring Boot + Thymeleaf + MySQL 8.0

## Yêu cầu

- Java 17+
- MySQL 8.0
- Maven

## Cài đặt

```bash
# 1. Tạo database
mysql -u root -p < db/setup.sql

# 2. Cấu hình kết nối trong src/main/resources/application.properties
#    sửa password nếu cần

# 3. Build & chạy
mvn clean package -DskipTests
java -jar target/fashion-recommendation-1.0.0.jar
```

Mở http://localhost:8080

## Cấu trúc

```
src/main/java/com/fashion/
  controller/   - MVC Controllers
  service/      - Business logic (K-Means, Cart, Product)
  model/        - JPA Entities
  repository/   - Spring Data JPA
  dto/          - Data Transfer Objects

src/main/resources/
  templates/    - Thymeleaf views
  static/       - CSS, JS
  application.properties - DB config
```
