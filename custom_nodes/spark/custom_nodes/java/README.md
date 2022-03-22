# Prerequisites

For development phase, you should have:

- A Kubernetes cluster reachable
- Punch Services (Operator & Artefact Server) deployed
- Clone this repository

Useful links:

- https://doc.punchplatform.com/Development_Guide/Punchline_Custom_Node/Spark_User_Nodes.html

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
├── punchline-spark-example.yaml
├── pom.xml
├── README.md
├── src
│   └── main
│       └── java
│           └── io
│               └── github
│                   └── starter
│                       └── punchline
│                           └── spark
│                               └── CustomFunction.java
|                               └── CustomSink.java
|                               └── CustomSource.java
```

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode with Docker


To test the punchline above in foreground mode simply run :

```sh
docker run --rm --entrypoint sparkctl-client \
    -v $PWD/punchline-spark-example.yaml:/usr/share/punch/punchline-spark.yaml \
    -v $PWD/target/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar:/usr/share/punch/extlib/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar \
    ghcr.io/punchplatform/punchline-spark:8.0-dev --job /usr/share/punch/punchline-spark.yaml
```

## Start your punchline in production mode with Kubernetes

Maven generates `./target/punchline-spark-starter-kit-1.0.0-artefact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-spark-starter-kit-1.0.0-artefact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f punchline-spark-example.yaml
```