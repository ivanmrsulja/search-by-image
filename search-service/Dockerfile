# Build stage
FROM maven:3.8.4-openjdk-11 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests


# Production stage
FROM openjdk:11

WORKDIR /app

COPY --from=build /app/target/search-service.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]