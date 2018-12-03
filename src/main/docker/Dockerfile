FROM openjdk:8-jdk-alpine
EXPOSE 8000:8000
ADD imdbNotepad-0.0.1-SNAPSHOT.jar imdbnotepad.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev", "imdbnotepad.jar"]