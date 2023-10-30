FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/mikes-app.jar mikes-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mikes-app.jar"]