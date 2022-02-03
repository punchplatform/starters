# Prerequisites

For development phase, you should have:

- Docker install locally or a Kubernetes cluster reachable
- Clone this repository

# Notes

**Note 1**

Dependencies that are specified in the provided `pom.xml` of this project is a constraints and should not be removed or have their version changed.

**Note 2**

Java 11 is expected to build this repository

**Note 3**

Use maven to build the jar

# Quick Start

Template hierarchy:

```sh
├── input_example.yaml
├── input_process_example.yaml
├── pom.xml
├── README.md
├── src
│   └── main
│       └── java
│           └── com
│               └── github
│                   └── starter
│                       └── punchline
│                           └── java
│                               ├── CustomFunction.java
│                               └── CustomSink.java
│                               └── CustomSource.java
│                               └── Config.java
```

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode with docker

To test the punchline above in foreground mode simply run : 

```sh
docker run -it \
    -v $PWD/target/punchline-java-starter-kit-1.0.0-jar-with-dependencies.jar:/opt/punch/extlib/storm/punchline-java-starter-kit-1.0.0-jar-with-dependencies.jar \
    -v $PWD/input_example.yaml:/data/input_example.yaml \
    ghcr.io/punchplatform/punchline-java:8.0-dev \
    /data/input_example.yaml
```

## Start your punchline in production mode on Kubernetes

A zip archive containing your jars and his metadata file can be build using maven : punchline-java-starter-kit-1.0.0-artifact.zip

You simply have to upload it to the Punch Artefact Server using this command :

```sh
curl -X POST "http://artifacts-service.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-java-starter-kit-1.0.0-artefact.zip -F override=true
```
Start your punchline on kubernetes 

```sh
kubectl apply -f $ROOT/input_example.yaml
```
