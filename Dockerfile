FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} word-combiner.jar
ENTRYPOINT ["java","-jar","/word-combiner.jar"]
