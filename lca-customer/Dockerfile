#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11.0.8-slim
#ADD target/customer-0.0.1-SNAPSHOT.jar customer-0.0.1-SNAPSHOT.jar
COPY --from=build /home/app/target/customer-0.0.1-SNAPSHOT.jar customer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/customer-0.0.1-SNAPSHOT.jar"]
EXPOSE 8200
