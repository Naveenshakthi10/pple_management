FROM openjdk:8-jdk-alpine

EXPOSE 8080

ADD target/peoplemanagement.jar peoplemangement.jar

ENTRYPOINT [ "java","-jar","peoplemanagement.jar" ]