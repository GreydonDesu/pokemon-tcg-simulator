# syntax=docker/dockerfile:1
# Stage 1: Build the frontend application
FROM gradle:8.14.1-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle wasmJsBrowserDistribution --no-daemon

# Stage 2: Serve the application using nginx
FROM nginx:alpine
COPY --from=builder /home/gradle/src/composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
