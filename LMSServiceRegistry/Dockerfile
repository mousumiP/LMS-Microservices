FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/lms-service-registry.jar lms-service-registry.jar
ENTRYPOINT ["java","-jar","/lms-service-registry.jar"]
