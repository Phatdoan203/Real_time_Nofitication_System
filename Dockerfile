FROM openjdk:17-jdk-slim

EXPOSE 8089

WORKDIR /app

# Copy file JAR vào container
COPY target/*.jar app.jar

# Cấu hình để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]