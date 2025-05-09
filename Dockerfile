FROM openjdk:17-jdk-slim
ARG JAR_FILE=demo/target/demo-0.0.1-SNAPSHOT.jar
##COPY ${JAR_FILE} bank_app.jar
COPY target/demo-0.0.1-SNAPSHOT.jar bank_app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","bank_app.jar"]