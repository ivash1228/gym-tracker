FROM eclipse-temurin:21

WORKDIR /app
COPY target/gym-tracker-0.0.1-SNAPSHOT.jar /app/gym-tracker.jar

ENTRYPOINT ["java", "-jar", "/app/gym-tracker.jar"]
