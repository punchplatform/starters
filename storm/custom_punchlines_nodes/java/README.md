# Prerequisites

For development phase, you should have:

- A Kubernetes cluster reachable
- Punch Services (Operator & Artefact Server) deployed
- Clone this repository

Useful links:

- https://doc.punchplatform.com/6.3.2/Development_Guide/Custom_Node/Storm_Custom_Nodes.html

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
├── input_example.yaml
├── input_process_example.yaml
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
resourcectl --url $ARTEFACT_URL upload --files target/punch-storm-node-starter-kit-1.0-artefact.zip
```

## Step three

```sh
kubectl apply -f $ROOT/input_example.punchline
```
