ARG JAVA_VERSION_TAG="jdk-11.0.14.1_1-alpine"
FROM adoptopenjdk/openjdk11:${JAVA_VERSION_TAG}
ARG SOURCES="src"
ARG MAVEN_LOCAL_REPOSITORY=/mvnhome/repository
WORKDIR /starter
RUN apk update &&\
    apk upgrade &&\
    apk --update-cache add make gettext libintl zip bash zip curl &&\
    rm -rf /var/cache/apk/* 
COPY mvnw mvnw
COPY INFO INFO
COPY Makefile Makefile
COPY pom.xml pom.xml
COPY src src
COPY tools tools
COPY metadata metadata
COPY .mvn .mvn
