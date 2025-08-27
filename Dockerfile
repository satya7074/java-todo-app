# Use OpenJDK 21 base image
FROM openjdk:21-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy built JAR into container
ARG JAR_FILE=target/todoapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Expose port (matches application.properties `server.port`)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]
