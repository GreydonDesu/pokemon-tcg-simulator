# Stage 1: Build the backend
FROM gradle:8.14.1-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

# Stage 2: Create a lightweight runtime container
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
