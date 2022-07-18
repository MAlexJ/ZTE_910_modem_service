FROM openjdk:18

# Copy build to root directory
COPY build/libs/ZTE_910_modem_service-0.0.1-SNAPSHOT.jar /app.jar

#run jav file
CMD ["java", "-jar", "/app.jar"]