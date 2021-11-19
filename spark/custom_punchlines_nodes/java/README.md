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
│           └── org
│               └── thales
│                   └── punch
│                       └── spark
│                           └── node
│                               └── starter
│                                   └── kit
│                                       └── InputNode.java
```

## Step one

```sh
mvn clean install
```

## Step two

```sh
resourcectl --url $ARTEFACT_URL upload --files target/punch-spark-java-node-starter-kit-1.0-SNAPSHOT-artefact.zip
```

## Step three

```sh
kubectl apply -f $ROOT/input_node_example.punchline
```