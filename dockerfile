FROM openjdk:11
ARG JAR_FILE=target/PiProject-1.0-RELEASE.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]