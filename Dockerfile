FROM openjdk:11-ea-9-slim
COPY target/demo1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]