FROM openjdk:11.0.8-slim
COPY target/customer-0.0.1-SNAPSHOT.jar /usr/local/app/
WORKDIR /usr/local/app/
EXPOSE 8200
ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]