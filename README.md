# Punch Starters

This repository aims at providing quick example code that can be used with a standalone version available at: https://punchplatform.com/download-area/

Some of these starters make use of punchpkg for interacting with our components.

**punchpkg** is a cli tool developped in Python3.6 which helps developpers interacting with our components during their developpement process.

# Hierarchy

At top level you will find application that you can extends. For example, interfacing your custom code implementation with our existing components, be it for spark, pyspark, storm, elastalert, ...

## Spark

In `spark` directory, you will find starters for implementing your own punchline node and udf. A starter is provided for `pyspark` and `spark` users.

## Storm

In `storm` directory, you will find a starter on how to write custom storm nodes in java.
