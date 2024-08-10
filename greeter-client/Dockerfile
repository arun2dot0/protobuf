FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/greeter-client-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080 9091

ENTRYPOINT ["java", "-jar", "app.jar"]
