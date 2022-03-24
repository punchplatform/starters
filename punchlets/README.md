# Punchlets Starters

This folder provides easy to start with punchlets examples. The Makefile lets you start the jaja punchline image to test
and see the effect of various punchlang use cases.

* *artifact* : demonstrates the loading of resources from a punch artifact.
* *arkoon* : demonstrates the use of grok and kv punchlang operators to parse arkoon logs.
* *standardgrok* : shows a more basic grok example.
* *customgroks* : illustrates the use of custom grok patterns.
* *geoip* : illustrates log enrichment using the geoip punchlang built-in function.
* *fileresource* : illustrates the use of JSON resource tuples.

To start any of these simply type in:

```sh
make geoip
``` 

## Punchlets or Parsers ?

Punchlets are simple yet powerful. Parsers let you package industrial tested and versioned sets of punchlets. Once at
ease with punchlets, checkout the [../parsers] sample.

## How it works

Each example consists of a punchlet (punchlet.punch) and a punchline (punchline.yaml).

In the punchline the data generator source is used to send in a log sample. The punchlet is then invoked, and a last
punchlet function is terminating the punchline graph to print the result to the screen .

For example here is the command launched for the arkoon sample:

```sh
	docker run \
		-v ${PWD}/arkoon/punchlet.punch:/punchlet.punch \
		-v ${PWD}/arkoon/punchline.yaml:/punchline.yaml \
		ghcr.io/punchplatform/punchline-java:8.0-dev \
		/punchline.yaml

```

Have fun !

