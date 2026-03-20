
FROM eclipse-temurin:24-jdk-alpine

RUN apk add --no-cache maven bash

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java","-jar","target/fitnessTracker-1.0-SNAPSHOT.jar"]