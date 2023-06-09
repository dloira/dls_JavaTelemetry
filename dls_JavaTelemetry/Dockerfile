FROM openjdk:8-jdk-alpine AS base
WORKDIR /app
EXPOSE 8080
#COPY ./target/metrics-prometheus-0.0.1-SNAPSHOT.jar /app
ARG JAR_FILE
COPY ./target/${JAR_FILE} /app
ENTRYPOINT ["java", "-jar", "metrics-prometheus-0.0.1-SNAPSHOT.jar"]