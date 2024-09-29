FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN  ./gradlew bootJar --no-daemon

FROM openjdk:17
WORKDIR /app
COPY build/libs/tfiApp_deploy.jar /app/tfiApp_deploy.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/tfiApp_deploy.jar"]