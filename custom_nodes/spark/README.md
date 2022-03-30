# PunchPlatform UDFs Integration

This repository is intended to be used as a starting point for developing your own Spark UDF. These UDF can be used
inside our PunchPlatform Sql Node

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

## Configuration

Let's run the configuration below:

```yaml
---
apiVersion: punchline.punchplatform.io/v2
kind: SparkPunchline
metadata:
  name: udf-before-java
spec:
  containers:
    serviceAccount: admin-user
    applicationContainer:
      image: ghcr.io/punchplatform/punchline-spark:8.0-dev
      imagePullPolicy: IfNotPresent
    resourcesInitContainer:
      image: ghcr.io/punchplatform/resourcectl:8.0-dev
      resourcesProviderUrl: http://artifacts-server.punch-artifacts:4245
  dependencies:
    - additional-spark-jar:com.github.punchplatform:punchplatform-udf-java-starter-kit:1.0.0
  engineSettings:
    spark.kubernetes.authenticate.driver.serviceAccountName: admin-user
    spark.additional.jar: punchplatform-udf-java-starter-kit-1.0-jar-with-dependencies.jar
  dag:
    - id: dataset_generator
    - type: generator
      kind: source
      settings:
        messages:
          - field1: "[1, 2, 3, 4]"
            age: 2
          - field1: "[15, 112, 3, 0]"
            age: 22
          - field1: "[15, 112, 3, 0]"
            age: 99
      out:
        - id: processor
    - type: sql
      kind: function
      component: processor
      settings:
        udfs:
          - class_name: com.github.punchplatform.udf.starter.kit.StrToArrayString
            function_name: punch_convertor
            schema_ddl: ARRAY<STRING>
          - class_name: com.github.punchplatform.udf.starter.kit.SumAggregate
            function_name: avg_age
        statements:
          - output_table_name: processed_udf
            statement: SELECT punch_convertor(field1) from dataset_generator_default
          - output_table_name: processed_udaf
            statement: SELECT avg_age(age) AS average_name from dataset_generator_default
      out:
        - id: show
    - type: show
      kind: sink
      id: show
```

# Quick Start

## Build

```sh
mvn clean install
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run :

```sh
docker run -it --rm --entrypoint sparkctl-client \
    -v $PWD/punchline.yaml:/usr/share/punch/punchline.yaml \
    -v $PWD/target/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar:/usr/share/punch/extlib/punchline-spark-starter-kit-1.0.0-jar-with-dependencies.jar \
    ghcr.io/punchplatform/punchline-spark:8.0-dev --job /usr/share/punch/punchline.yaml
```

## Start your punchline in production mode with Kubernetes

Maven generates `./target/punchline-udf-spark-starter-kit-1.0.0-artifact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-udf-spark-starter-kit-1.0.0-artifact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f after_udf_helloworld.yaml
```

# Conclusion

As you can see, we developed a UDF function with the ability to translate a string representing an array to an array of
string !

Much more can be done, for instance, integrating parsing logic as UDFs!

