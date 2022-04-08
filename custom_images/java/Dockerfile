FROM ghcr.io/punchplatform/punchline-java:8.0-dev

COPY punchlets/print.punch /usr/share/punch/resources/print.punch
COPY punchline/punchline.yaml /data/punchline.yaml
COPY log4j2 /opt/punch/log4j2/

ENTRYPOINT ["/bin/bash" , "start.sh", "/data/punchline.yaml"]