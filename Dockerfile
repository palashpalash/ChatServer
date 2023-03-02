FROM openjdk:15-oracle
EXPOSE 8080
ADD target/spring-bbot-docker.war spring-bbot-docker.war
ENTRYPOINT ["java","-jar","spring-bbot-docker.war"]