FROM openjdk:11
MAINTAINER Rioa

COPY *.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]