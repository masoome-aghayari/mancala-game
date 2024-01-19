FROM openjdk:17
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
CMD java target/classes/com/mancala/MancalaGameApplication.class
