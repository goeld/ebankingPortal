FROM openjdk:14-ea-12-jdk-alpine3.10
ARG JAR_FILE=target/ebankingPortal-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]