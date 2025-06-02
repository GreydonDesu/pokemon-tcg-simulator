# Use a lightweight OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Build argument to specify the JAR file location
ARG JAR_FILE=build/libs/*.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
