FROM openjdk:8-jdk-alpine
MAINTAINER Boi
COPY target/shorterurl-0.0.1-SNAPSHOT.jar shorterurl-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/shorterurl-0.0.1-SNAPSHOT.jar"]