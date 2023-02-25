FROM gradle:7.3.3-jdk17-alpine as build
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build -x test

FROM openjdk:17-jdk-alpine3.14 as run
COPY --from=build /home/gradle/source/build/libs/macadamia-nut-1.4.1.jar /app/
COPY /config/application.yml /app/
WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","macadamia-nut-1.4.1.jar", "--spring.config.location=file:./application.yml", "--spring.main.allow-bean-definition-overriding=true"]
