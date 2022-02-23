# Prerequisites

For development phase, you should have:

- Maven > 3.6
- Docker install locally, or a Kubernetes cluster reachable
- Clone this repository

# Notes

**Note 1**

Dependencies that are specified in the provided `pom.xml` of this project are mandatory.
They cannot be removed or have their version changed.

# Quick Start

## Build

```sh
mvn clean install
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run : 

```sh
docker run --rm -it \
    -v $PWD/target/punchline-python-starter-kit-1.0.0.pex:/usr/share/punch/extlib/punchline-python-starter-kit-1.0.0.pex \
    -v $PWD/gen_print_example.yaml:/data/gen_print_example.yaml \
    ghcr.io/punchplatform/punchline-python:8.0-dev \
    /data/gen_print_example.yaml
```

## Start your punchline in production mode with Kubernetes

Maven generates `./target/punchline-python-starter-kit-1.0.0-artifact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-service.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-python-starter-kit-1.0.0-artifact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f $ROOT/gen_print_example.yaml
```
