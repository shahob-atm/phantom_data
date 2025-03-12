FROM openjdk:17-jdk-slim

WORKDIR /app

# Maven'ni o‘rnatish
RUN apt-get update && apt-get install -y maven

# Loyiha fayllarini konteynerga ko‘chirish
COPY . .

# **To‘g‘ri path** ga build qilish
RUN mvn clean package -DskipTests

# **Yangi build qilingan .jar faylni olish**
COPY out/phantom_data.jar app.jar

# **Seeder kutubxonasini qo‘shish**
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
