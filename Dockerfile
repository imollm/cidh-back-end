FROM openjdk:13-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ./build/libs/macadamia-nut-0.0.1.jar app.jar

COPY ./config/application.xml application.xml

ENTRYPOINT ["java","-jar","/app.jar", "--spring.config.location=file:/application.yml", "--spring.main.allow-bean-definition-overriding=true"]
