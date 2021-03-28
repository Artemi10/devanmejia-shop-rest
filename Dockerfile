FROM openjdk:11
EXPOSE 8081
ADD target/devamejiaSpringBootProject-1.0-SNAPSHOT.jar devamejiaSpringBootProject-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "devamejiaSpringBootProject-1.0-SNAPSHOT.jar"]
