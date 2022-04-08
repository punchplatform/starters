# PunchPlatform UDFs Integration

This repository is intended to be used as a starting point for developing your own Spark Nodes and UDF. These UDF can be used
inside our PunchPlatform Sql Node.

# Prerequisite

- Basic knowledge on Spark DataTypes/Scala DataTypes
- Basic knowledge on Spark API: UDF
- Docker install locally, or a Kubernetes cluster reachable

# Notes

## Note 1

Changing *metadata.yml* information requires updating variables define in

[INFO](./INFO) :

- GROUP_ID
- ARTIFACT_ID
- VERSION

In case you desire to include your project in a CI, these variables can be updated inline by passing them as argument:

```sh
make VERSION="unstable" artifact
```

**GROUP_ID** and **ARTIFACT_ID** value should match [pom.xml](pom.xml):

- artifactId
- groupId

and directory hierarchy in `src/main/java/...`

## Demonstration

The UDF function in this repository takes as input a String which follows the following pattern:

> "[1.0, 2, 5.5, 8.6]"

this UDF produces as output:

> WrappedArray(1.0, 2, 5.5, 8.6)


# Quick Start

```sh
# Read the doc
make help
```

## Package

### You are a developer

```sh
# you are required to have openjdk11 installed and configured
make artifact # check target directory
```

### You are a user

Building the zip archive using your desired java jdk version

```sh
# build with defaults
make
# build with another java (not advised)
make JAVA_VERSION_TAG=jdk-11.0.14.1_1-alpine
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode run:

```sh
# using latest-dev engine
make run
# using latest-stable engine
make run ENGINE_IMG=ghcr.io/punchplatform/punchline-spark:8.0-latest
```

## Start your punchline in production mode with Kubernetes

### Using commands

A zip archive is generated as `target/punchline-spark-starter-kit-1.0.0-artifact.zip`.

You have to upload it to the Punch Artifacts Server using this command:

```sh
make upload-artifact ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes:

```sh
kubectl apply -f punchline.yaml
```

# Conclusion

As you can see, we developed a UDF function with the ability to translate a string representing an array to an array of
string !

Much more can be done, for instance, integrating parsing logic as UDFs!

