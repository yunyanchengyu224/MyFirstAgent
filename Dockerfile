FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp package -DskipTests

FROM eclipse-temurin:25-jre-alpine

WORKDIR /app
RUN addgroup -S app && adduser -S -G app app
COPY --from=build /workspace/target/MyFirstAgent-1.0-SNAPSHOT.jar /app/app.jar
RUN chown app:app /app/app.jar

USER app
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]