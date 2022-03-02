# Punch Starters

This repository is a software development kit for Punch developers. 

It provides easy-to-start-with samples for various use cases, implemented using
java, python and punchlang functions.

The punch online documentation is available here: https://punch-1.gitbook.io/punch-doc/.

## Punchlets

Checkout the [./punchlets] guide. Writing a punchlet is easy, testing it as easy.

## Parsers

To write robust and industrial log parser requires one or more punchlet(s), some additional
enrichment resource files, some documentation and a way to package everything so that your parser
can be easily deployed on a production punch. 

The parser SDK provides you with a maven toolkit to take care of all these packaging issues.
It also provides you with a unit test framework. 

Checkout out the [./parsers] guide.

## Docker Image

One easy way to package your punch application is to generate your own image together with the required
punchlets or custom function. Check out the [./custom_image] folder.

## Custom Functions Development

You can write your own Java or python functions using the punch SDK. Checkout out the [./custom_nodes]

## Tools

A few helper tools are provided to ease the day to day 
Some lightweight tools are provided (shell helpers, because all the complex things are in docker...).
To include these tools in your PATH:

	. activate.sh
