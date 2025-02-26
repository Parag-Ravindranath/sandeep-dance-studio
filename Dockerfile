# Use a JDK base image instead of JRE
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application jar file into the container
COPY build/libs/sds-*.war app.war

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.war"]
