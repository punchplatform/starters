# Punch Starters

This repository is a software development kit for Punch developers.
It provides easy to start with examples for various use cases, implemented using
java, python and punchlang parsers.

## Repository Organisation

* custom_image : explains how to create a custom container image that embeds your own resources. This is useful if you have simple static punch applications, and/or if you have your own CI/CD at hand.
* custom_nodes : provides samples of custom nodes based on different engine : java, python, spark, flink. It also provides useful commands to help you in development or production mode.
* parsers : provides a complete punch parser example.  It also provides useful commands to help you in development and production mode.
* examples : provides a collection of punchlet and punchline examples to quickly find your way in solving your use cases with the right pattern.
* simulator : provides a simulator example

## Tools

Some lightweight tools are provided (shell helpers, because all the complex things are in docker...).
To include these tools in your PATH:

	. activate.sh