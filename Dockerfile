# --- STAGE 1: Build the Java application ---
# Use a Maven image with Java 17 installed to compile our code
FROM maven:3.9-eclipse-temurin-17 AS build

# Set the working directory inside the container to /app
WORKDIR /app

# Copy project files (dependencies list and source code) into the container
COPY pom.xml .
COPY src ./src

# Build the project and create the executable .jar file (skipping tests for speed)
RUN mvn clean package -DskipTests


# --- STAGE 2: Create the lightweight runtime container ---
# Use a smaller Java 17 runtime image (no heavy Maven tools needed anymore)
FROM eclipse-temurin:17-jre

# Set the working directory for the runtime container
WORKDIR /app

# Copy only the compiled .jar file from STAGE 1 into this clean container
COPY --from=build /app/target/*.jar app.jar

# Inform Docker that the app inside listens on port 8080 by default
EXPOSE 8080

# The command to start the Spring Boot app when the container boots up
ENTRYPOINT ["java", "-jar", "app.jar"]