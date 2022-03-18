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
├── input_node_example.yaml
├── pom.xml
├── README.md
├── src
│   └── main
│       └── java
│           └── com
│               └── github
│                   └── punchplatform
│                       └── spark
│                           └── node
│                               └── starter
│                                   └── kit
│                                       └── InputNode.java
```

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode

Import your node : 

```sh
ROOT=$(pwd)  # directory of this README.md
cp $ROOT/target/punch-spark-java-node-starter-kit-1.0.0-jar-with-dependencies.jar $PUNCHPLATFORM_INSTALL_DIR/extlib/spark/
```

Start your punchline in foreground mode :  

```sh
punchlinectl start -p $ROOT/input_node_example.yaml
```

## Start your punchline in production mode

Import your artefact : 

```sh
resourcectl --url $ARTEFACT_URL upload --files target/punch-spark-java-node-starter-kit-1.0.0-artefact.zip
```
Start your punchline on kubernetes 

```sh
kubectl apply -f $ROOT/input_node_example.yaml
```