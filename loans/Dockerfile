FROM eclipse-temurin:20-jdk-alpine AS build

MAINTAINER ahmet.com

COPY target/loans-0.0.1-SNAPSHOT.jar loans-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "loans-0.0.1-SNAPSHOT.jar"]