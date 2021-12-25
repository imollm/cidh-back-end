FROM gradle:7.3.3-jdk17-alpine as build
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build
#RUN gradle bootJar --parallel -i

FROM openjdk:17-jdk-alpine3.14 as run
#COPY ./build/libs/macadamia-nut-0.0.1.jar app.jar
COPY --from=build /home/gradle/source/build/libs/macadamia-nut-0.0.1.jar /app/
COPY --from=build /home/gradle/source/config/application.yml /app/
WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","macadamia-nut-0.0.1.jar", "--spring.config.location=file:/application.yml", "--spring.main.allow-bean-definition-overriding=true"]
