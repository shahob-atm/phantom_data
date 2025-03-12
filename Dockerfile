FROM openjdk:17-jdk-slim

WORKDIR /app

# Maven build qilish
COPY . .
RUN ./mvnw clean package -DskipTests

# `target/` ichidagi .jar faylni to‘g‘ri nom bilan ko‘rsating
COPY target/phantom_data.jar app.jar

# Agar `libs/seeder.jar` kerak bo‘lsa, uni ham nusxalash
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]