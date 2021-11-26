# PunchPlatform UDFs Integration

This repository is intended to be used as a starting point for developing your own Spark UDF.
These UDF can be used inside our PunchPlatform Sql Node

Note, although the UDF is coded using java, you should be able to use it within the pyspark provided by punch by installing a UDF jar with `install-dependencies` command.

## Prerequisite

-   Basic knowledge on Spark DataTypes/Scala DataTypes
-   Basic knowledge on Spark API: UDF

## Demonstration

![](../../resources/udf/helloworld_udf.gif)

The UDF function in this repository takes as input a String which follows the following pattern:

>   "[1.0, 2, 5.5, 8.6]"

this UDF produces as output:

> WrappedArray(1.0, 2, 5.5, 8.6)

## Configuration

Let's run the configuration below:

```yaml
---
apiVersion: punchline.gitlab.thalesdigital.io/v1
kind: Sparkline
metadata:
  name: udf-before-java
spec:  
  image: ghcr.io/punchplatform/sparkline:7.0.1-SNAPSHOT
  initContainerImage: ghcr.io/punchplatform/resourcectl:7.0.1-SNAPSHOT
  initContainerUrl: http://artifacts-service.punch-gateway-ns:4245
  imagePullPolicy: IfNotPresent
  serviceAccount: admin-user
  garbageCollect: false
  implementation: java
  dependencies:
  - additional-spark-jar:org.thales.punch:punchplatform-udf-java-starter-kit:1.0.0
  settings:
    spark.kubernetes.authenticate.driver.serviceAccountName: admin-user
    spark.additional.jar: punchplatform-udf-java-starter-kit-1.0-jar-with-dependencies.jar
  punchline:
    dag:
    - type: dataset_generator
      component: dataset_generator
      settings:
        input_data: 
          - field1: "[1, 2, 3, 4]"
            age: 2
          - field1: "[15, 112, 3, 0]"
            age: 22
          - field1: "[15, 112, 3, 0]"
            age: 99 
      publish:
        - stream: data
    - type: sql
      component: processor
      settings:
        register_udf:
          - class_name: org.thales.punch.udf.starter.kit.StrToArrayString
            function_name: punch_convertor
            schema_ddl: ARRAY<STRING>
          - class_name: org.thales.punch.udf.starter.kit.SumAggregate
            function_name: avg_age
        statement_list:
          - output_table_name: processed_udf
            statement: SELECT punch_convertor(field1) from dummy_data
          - output_table_name: processed_udaf
            statement: SELECT avg_age(age) AS average_name from dummy_data
      subscribe:
      - component: dataset_generator
        stream: data      
      publish:
      - stream: data  
    - type: show
      component: show
      settings: {}
      subscribe:
      - component: processor
        stream: data
      publish: []
```

# Quick Start

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode

Import your node : 

```sh
ROOT=$(pwd)  # directory of this README.md
cp $ROOT/target/punchplatform-udf-java-starter-kit-1.0.0.pex $PUNCHPLATFORM_INSTALL_DIR/extlib/spark/
```

Start your punchline in foreground mode :  

```sh
punchlinectl start -p $ROOT/after_udf_helloworld.yaml
```

## Start your punchline in production mode

Import your artefact : 

```sh
resourcectl --url $ARTEFACT_URL upload --files target/punchplatform-udf-java-starter-kit-1.0.0-artefact.zip
```
Start your punchline on kubernetes 

```sh
kubectl apply -f $ROOT/after_udf_helloworld.yaml
```


# Conclusion

As you can see, we developped an UDF function with the ability to translate a string representing an array to an array of string !

Much more can be done, for instance, integrating parsing logic as UDFs!

