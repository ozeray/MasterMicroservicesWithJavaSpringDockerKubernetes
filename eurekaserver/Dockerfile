FROM eclipse-temurin:20-jdk-alpine AS build

MAINTAINER ahmet.com

COPY target/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "eurekaserver-0.0.1-SNAPSHOT.jar"]