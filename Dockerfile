FROM amazoncorretto:21

WORKDIR /app
COPY target/gym-tracker-0.0.1-SNAPSHOT.jar /app/gym-tracker.jar
HEALTHCHECK --interval=10s --timeout=10s --start-period=10s --retries=20 \
  CMD curl -sf http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java", "-jar", "/app/gym-tracker.jar"]
