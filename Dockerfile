FROM eclipse-temurin:17-jdk as build

COPY . /app
WORKDIR /app

RUN chmod +x mvnw
RUN ./mvnw package -DskipTests
RUN mv -f target/*.war app.war

FROM eclipse-temurin:17-jre

ARG PORT
ENV PORT=${PORT}

COPY --from=build /app/app.war .

RUN useradd runtime
USER runtime

ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-war", "app.war" ]