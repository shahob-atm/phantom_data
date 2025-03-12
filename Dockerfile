FROM openjdk:17-jdk-slim

WORKDIR /app

# Kodni konteynerga nusxalash
COPY . .

# Maven build qilish
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Jar faylni nusxalash
COPY target/*.jar app.jar

# Agar `libs/seeder.jar` kerak bo‘lsa, uni ham nusxalash
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
