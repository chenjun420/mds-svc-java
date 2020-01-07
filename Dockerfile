FROM openjdk:8-jre-alpine
MAINTAINER Harry.Chan <chenpi_cn@hotmail.com>

ARG JAR_FILE
RUN mkdir /pts
WORKDIR /pts
ADD target/${JAR_FILE} /pts/${JAR_FILE}
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "${JAR_FILE}"]