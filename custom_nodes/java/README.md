# Prerequisites

For development phase, you should have:

- Java 11
- Maven > 3.6
- Docker install locally, or a Kubernetes cluster reachable
- Clone this repository

# Notes

**Note 1**

Dependencies that are specified in the provided `pom.xml` of this project are mandatory.
They cannot be removed or have their version changed.

**Note 2**

Currently, the Punch API is in 8.0-SNAPSHOT version.

To use SNAPSHOT versions, you have to add Maven Central Snapshot Repository to your `~/.m2/settings.xml` :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<profiles>
	  <profile>
	     <id>allow-snapshots</id>
		<activation><activeByDefault>true</activeByDefault></activation>
	     <repositories>
	       <repository>
		 <id>snapshots-repo</id>
		 <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
		 <releases><enabled>false</enabled></releases>
		 <snapshots><enabled>true</enabled></snapshots>
	       </repository>
	     </repositories>
	   </profile>
	</profiles>
</settings>
```

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

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run : 

```sh
docker run --rm -it \
    -v $PWD/target/punchline-java-starter-kit-1.0.0-jar-with-dependencies.jar:/usr/share/punch/extlib/punchline-java-starter-kit-1.0.0-jar-with-dependencies.jar \
    -v $PWD/input_example.yaml:/data/input_example.yaml \
    ghcr.io/punchplatform/punchline-java:8.0-dev \
    /data/input_example.yaml
```

## Start your punchline in production mode with Kubernetes

Maven generates `./target/punchline-java-starter-kit-1.0.0-artifact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-service.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-java-starter-kit-1.0.0-artifact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f $ROOT/input_example.yaml
```
