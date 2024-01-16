FROM openjdk:17
WORKDIR /tmp
COPY --from=build /Users/masoomeaghayari/IdeaProjects/mancala-game/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
CMD java target/classes/com/mancala/MancalaGameApplication.class

FROM ubuntu:latest
LABEL authors="masoomeaghayari"

ENTRYPOINT ["top", "-b"]