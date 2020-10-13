FROM openjdk:11.0.8-slim
COPY target/gateway-0.0.1-SNAPSHOT.jar /usr/local/app/
WORKDIR /usr/local/app/
EXPOSE 8762
ENTRYPOINT ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]