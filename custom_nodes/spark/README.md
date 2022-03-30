# PunchPlatform UDFs Integration

This repository is intended to be used as a starting point for developing your own Spark Nodes and UDF. These UDF can be used
inside our PunchPlatform Sql Node.

Note, although the UDF is coded using java, you should be able to use it within the pyspark provided by punch by
installing a UDF jar with `install-dependencies` command.

## Prerequisite

- Basic knowledge on Spark DataTypes/Scala DataTypes
- Basic knowledge on Spark API: UDF

## Demonstration

The UDF function in this repository takes as input a String which follows the following pattern:

> "[1.0, 2, 5.5, 8.6]"

this UDF produces as output:

> WrappedArray(1.0, 2, 5.5, 8.6)


# Quick Start

## Build

```sh
make build
```

or


```sh
mvn clean install
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run :

```sh
make run
```

or

```sh
docker run -it --rm --entrypoint sparkctl-client \
    -v $PWD/punchline.yaml:/data/punchline.yaml \
    -v $PWD/target/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar:/usr/share/punch/extlib/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar \
    ghcr.io/punchplatform/punchline-spark:8.0-dev --job /data/punchline.yaml
```

## Start your punchline in production mode with Kubernetes

### Using Makefile

To upload your nodes and apply your punchline :

```sh
make apply
```

To check your logs :

```sh
make logs
```

To delete your punchline :

```sh
make delete
```

### Using commands

Maven generates `./target/punchline-spark-starter-kit-1.0.0-artifact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-spark-starter-kit-1.0.0-artifact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f punchline.yaml
```

# Conclusion

As you can see, we developed a UDF function with the ability to translate a string representing an array to an array of
string !

Much more can be done, for instance, integrating parsing logic as UDFs!

