# Build stage with Maven + JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Use Maven to build the project and skip tests
RUN mvn -q -e -DskipTests package

# Runtime stage with JRE 17
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/desafioFase1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
