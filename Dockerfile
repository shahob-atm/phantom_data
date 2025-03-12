FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar
COPY libs/your-jar-name.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
