FROM openjdk:17-jdk-slim

WORKDIR /app

# Maven build qilish uchun zarur paketlar
RUN apt-get update && apt-get install -y maven

# Loyiha fayllarini konteynerga nusxalash
COPY . .

# Jar build qilish
RUN mvn clean package -DskipTests

# Jar faylni to‘g‘ri joydan olish
COPY out/artifacts/phantom_data_jar/phantom_data.jar app.jar

# Seeder library'ni qo‘shish
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

