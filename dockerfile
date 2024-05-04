FROM openjdk:11
ARG NEXUS_URL=http://192.168.33.10:8081/repository/maven-releases/PFE/Spring/PFEProject/2.2/PFEProject-2.2.jar
RUN wget -O /PFEProject-2.2.jar $NEXUS_URL
CMD ["java", "-jar", "/PFEProject-2.2.jar"]
EXPOSE 8082