#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
FROM gradle:8.10.1-jdk17 AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew clean dependencies --no-daemon
COPY src src
#COPY . .
RUN ./gradlew clean bootJar --no-daemon

FROM openjdk:17
WORKDIR /app
COPY build/libs/tfiApp_deploy.jar /app/tfiApp_deploy.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tfiApp_deploy.jar"]