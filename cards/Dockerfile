FROM eclipse-temurin:20-jdk-alpine AS build

MAINTAINER ahmet.com

COPY target/cards-0.0.1-SNAPSHOT.jar cards-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "cards-0.0.1-SNAPSHOT.jar"]