# Stage 1: Build the frontend application
FROM gradle:8.14.2-jdk21 AS builder

# Copy Gradle files, and set working directory
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the project
RUN --mount=type=cache,target=/home/gradle/.gradle gradle wasmJsBrowserDistribution

# Stage 2: Serve the application using nginx
FROM nginx:alpine

# Copy the built application to the nginx HTML directory
COPY --from=builder /home/gradle/src/composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
