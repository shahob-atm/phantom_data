FROM openjdk:17-jdk-slim

WORKDIR /app

# **LOYIHANI KONTEYNERGA NUSXALASH**
COPY . .

# **MAVEN BILAN .JAR FAYLNI BUILD QILISH**
RUN mvn clean package -DskipTests

# **TARGET'DAN .JAR FAYLNI KO‘CHIRISH**
COPY target/phantom_data.jar app.jar

# **SEEDER KUTUBXONASINI QO‘SHISH**
COPY libs/seeder.jar libs/seeder.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
