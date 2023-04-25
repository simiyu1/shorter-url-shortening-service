
# Use custom OpenJDK 20 base image
FROM openjdk:20
MAINTAINER Boi

# Copy the jar file to the container
COPY target/shorterurl-0.0.1-SNAPSHOT.jar shorterurl-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "/shorterurl-0.0.1-SNAPSHOT.jar"]
