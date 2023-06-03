# Build stage
FROM maven:3.9.2-amazoncorretto-11 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests


# Production stage
FROM amazoncorretto:11

WORKDIR /app

COPY --from=build /app/target/search-service.jar app.jar

EXPOSE 8081

CMD ["sh", "-c", "sleep 30 && java -jar app.jar"]
