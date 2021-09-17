FROM openjdk:14-buster
RUN apt-get update && \
    apt-get -y install sudo
RUN sudo wget -P /tmp -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
RUN chmod 777 /dd-java-agent.jar

ARG JAR_FILE=./target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} app.jar
CMD java \
    -javaagent:dd-java-agent.jar \
    -jar /app.jar
