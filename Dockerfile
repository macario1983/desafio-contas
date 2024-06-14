FROM gradle:8.0.0-jdk17 AS build
WORKDIR /home/gradle/project
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle clean build --no-daemon
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]