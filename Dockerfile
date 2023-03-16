#FROM openjdk:17.0.2-slim as runtime
FROM arm32v7/eclipse-temurin:17.0.1_12-jdk-focal
COPY build/libs/Explorer-*.jar explorer.jar
EXPOSE 8020
CMD java ${JAVA_OPTS} -jar explorer.jar