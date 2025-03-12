FROM openjdk:17-jdk-slim

WORKDIR /app

# `target/` ichidagi .jar faylni aniq nomi bilan ko‘rsating!
COPY target/phantom_data.jar app.jar

# Agar `libs/seeder.jar` kerak bo‘lsa, uni ham nusxalash
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
