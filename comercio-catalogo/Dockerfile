FROM maven:3.9.12-eclipse-temurin-21-alpine AS build
COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn clean install -DskipTests
# RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /app/target/comercio-catalogo-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
# COPY --from=build /app/target/*.jar app.jar
# ENTRYPOINT [ "java", "-jar", "app.jar" ]
CMD [ "java", "-jar", "app.jar" ]