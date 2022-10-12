# Prerequisites

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

## Note 2

Dependencies that are specified in the provided `pom.xml` of this project are mandatory. They cannot be removed or have
their version changed.

## Note 3

This project uses per based project maven settings with preconfigured settings for fetching internal dependencies.

Edit [settings.xml](.mvn/settings.xml) if you need more customization.

# Quick Start

```sh
# Read the doc
make help
```

## Package

### You are a developer

```sh
# you are required to have openjdk11 installed and configured
make build # check target directory
```

### You are a user

Building the zip archive

```sh
# build in a docker environment
make docker-build # check target directory
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode run:

```sh
# using latest-dev engine
make run
# using latest-stable engine
make run PUNCHLINE_IMG=ghcr.io/punchplatform/punchline-java:8.0-latest
```

### Using commands

A zip archive is generated as `target/punchline-java-starter-kit-1.0.0.zip`.

You have to upload it to the Punch Artifacts Server using this command:

```sh
make upload ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes:

```sh
kubectl apply -f punchline.yaml
```
