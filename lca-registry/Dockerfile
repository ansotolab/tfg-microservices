FROM openjdk:11.0.8-slim
COPY target/registry-0.0.1-SNAPSHOT.jar /usr/local/app/
WORKDIR /usr/local/app/
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "registry-0.0.1-SNAPSHOT.jar"]