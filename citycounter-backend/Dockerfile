# Use Eclipse Temurin JDK 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/*.jar app.jar

# Expose the application's port (optional, useful for documentation)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
