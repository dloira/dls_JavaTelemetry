FROM openjdk:8-jdk-alpine AS base
WORKDIR /app
EXPOSE 8080
ARG JAR_FILE
COPY ./dls_JavaTelemetry/target/${JAR_FILE} /app
ENTRYPOINT ["java", "-jar", "dls_JavaTelemetry-0.0.1-SNAPSHOT.jar"]