FROM eclipse-temurin:20-jdk-alpine AS build

MAINTAINER ahmet.com

COPY target/configserver-0.0.1-SNAPSHOT.jar configserver-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "configserver-0.0.1-SNAPSHOT.jar"]