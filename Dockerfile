FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

COPY gradle ./gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .

COPY src ./src

RUN sed -i 's/\r$//' gradlew
RUN chmod +x gradlew
RUN ./gradlew build -x test

FROM openjdk:17-jdk-alpine AS app

WORKDIR /app

COPY --from=build /app/build/libs/mikes.jar .

ARG DB_HOST=mikes-db-container
ARG DB_PORT=5432
ARG DB_NAME=mikes-db
ARG DB_USER=root
ARG DB_PASSWORD=change_it

ENV DB_HOST=$DB_HOST
ENV DB_PORT=$DB_PORT
ENV DB_NAME=$DB_NAME
ENV DB_USER=$DB_USER
ENV DB_PASSWORD=$DB_PASSWORD

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "mikes.jar"]