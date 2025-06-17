# syntax=docker/dockerfile:1

# Stage 1: Build the frontend application
FROM gradle:8.14.1-jdk17 AS builder

# Set the working directory
WORKDIR /home/gradle/src

# Copy only the Gradle wrapper and build files first to cache dependencies
COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle gradlew .
COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts ./

# Pre-download dependencies (this step will be cached unless dependencies change)
RUN ./gradlew --no-daemon wasmJsBrowserDistribution || true

# Copy the rest of the source code
COPY --chown=gradle:gradle . .

# Build the project
RUN ./gradlew wasmJsBrowserDistribution --no-daemon

# Stage 2: Serve the application using nginx
FROM nginx:alpine

# Copy the built application to the nginx HTML directory
COPY --from=builder /home/gradle/src/composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
