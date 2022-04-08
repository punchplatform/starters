# Prerequisites

- Docker install locally, or a Kubernetes cluster reachable
- Clone this repository

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

Currently, the Punch API is in 8.0-dev version.

To use SNAPSHOT versions, you have to add Maven Central Snapshot Repository to your `~/.m2/settings.xml` :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd"
          xmlns="http://maven.apache.org/SETTINGS/1.2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <profiles>
        <profile>
            <id>allow-snapshots</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
                <repository>
                    <id>snapshots-repo</id>
                    <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
                    <releases>
                        <enabled>false</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>
</settings>
```

# Quick Start

## Package

### You are a developer

```sh
# you are required to have openjdk11 installed and configured
make artifact # check target directory
```

### You are a user

Building the zip archive using your desired java jdk version

```sh
make docker-build JAVA_VERSION_TAG=jdk-11.0.14.1_1-alpine
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode run:

```sh
# using latest-dev engine
make run
# using latest-stable engine
make run ENGINE_IMG=ghcr.io/punchplatform/punchline-java:8.0-latest
```

### Using commands

A zip archive is generated as `target/punchline-java-starter-kit-1.0.0.zip`.

You simply have to upload it to the Punch Artifacts Server using this command:

```sh
make upload-artifact ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes:
```sh
kubectl apply -f punchline.yaml
```
