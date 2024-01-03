FROM openjdk:17-alpine
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Djasypt.encryptor.password=NzY2NA==", "-jar", "app.jar"]