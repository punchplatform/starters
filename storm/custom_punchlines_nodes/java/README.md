# Prerequisites

For development phase, you should have:

- standalone installed
- clone this repository

Useful links:

- https://doc.punchplatform.com/6.3.2/Development_Guide/Custom_Node/Storm_Custom_Nodes.html

# Notes

**Note 1**

Dependencies that are specified in the provided `pom.xml` of this project is a constraints and should not be removed or have their version changed.

**Note 2**

In your punchline don't forget to specify `additional_jars` to add your custom node as a dependency at runtime.

**Note 3**

Java8 is expected to be installed by the user running the punchline.

**Note 4**

Use maven to build the jar

# Quick Start

Template hierarchy:

```sh
├── input_example.punchline
├── input_process_example.punchline
├── pom.xml
├── README.md
├── src
│   └── main
│       └── java
│           └── org
│               └── thales
│                   └── punch
│                       └── storm
│                           └── node
│                               └── starter
│                                   └── kit
│                                       ├── InputNode.java
│                                       └── ProcessingNode.java
```

## Step one

```sh
mvn clean install
```

## Step two

```sh
ROOT=$(pwd)  # directory of this README.md
cp $ROOT/target/punch-storm-node-starter-kit-1.0-jar-with-dependencies.jar $PUNCHPLATFORM_INSTALL_DIR/extlib/storm/
```

## Step three

```sh
punchlinectl start -p $ROOT/input_example.punchline
```
