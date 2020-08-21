# Prerequisites

For development phase, you should have:

- standalone installed
- clone this repository

Useful links:

- https://doc.punchplatform.com/Development_Guide/Punchline_Custom_Node/Spark_User_Nodes.html

# Notes

**Note 1**

Dependencies that are specified in the provided `pom.xml` of this project is a constraints and should not be removed or have their version changed.

**Note 2**

In your punchline don't forget to specify `spark.additional.jar` to add your custom node as a dependency at runtime.

**Note 3**

Java8 is expected to be installed by the user running the punchline.

**Note 4**

Use maven to build the jar

# Quick Start

Template hierarchy:

```sh
├── input_node_example.punchline
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
ROOT=$(pwd)  # directory of this README.md
punchpkg spark install $ROOT/target/punch-spark-node-starter-kit-1.0-jar-with-dependencies.jar
```

## Step three

```sh
punchlinectl start -p $ROOT/input_node_example.punchline
```