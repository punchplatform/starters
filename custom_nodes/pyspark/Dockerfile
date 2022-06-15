FROM python:3.10.4-slim

WORKDIR /starter

RUN DEBIAN_FRONTEND=noninteractive apt-get update && \
    apt-get install --no-install-suggests --no-install-recommends --yes make gettext-base zip && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean

COPY . .

RUN make build