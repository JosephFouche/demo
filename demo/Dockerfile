FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bank_app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","bank_app.jar"]