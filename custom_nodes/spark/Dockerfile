FROM adoptopenjdk/openjdk11:jdk-11.0.14.1_1-alpine

WORKDIR /starter

RUN apk update && \
    apk upgrade && \
    apk --update-cache add make gettext libintl zip bash zip curl && \
    rm -rf /var/cache/apk/*

COPY . .

RUN make build MAVEN_LOCAL_REPOSITORY=/mvnhome