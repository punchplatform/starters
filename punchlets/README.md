# Punchlets Starters

This folder provides easy-to-start-with punchlets examples. The Makefile lets you start the java punchline
and see the effect of various punchlang use cases.

* *artifact* : demonstrates the loading of resources from a punch artifact.
* *arkoon* : demonstrates the use of grok and kv punchlang operators to parse arkoon logs.
* *stdgroks2* : shows a more basic grok2 example.
* *customgroks* : illustrates the use of custom grok2 patterns.
* *geoip* : illustrates log enrichment using the geoip punchlang built-in function.
* *fileresource* : illustrates the use of JSON resource tuples.

To start them all simply type in make.
To start any of these simply type in:
```sh
make geoip
``` 

On linux, you can use '<tab>' after typing `make ` to see the name of all tests you can run through the makefile.

## Punchlets or Parsers ?

Punchlets are simple yet powerful. Parsers let you package industrial tested and versioned sets of punchlets. Once at
ease with punchlets, checkout the [parsers](../../../parsers) sample.

## How it works

Each example consists of a punchlet (`punchlet.punch`) and a punchline (`punchline.yaml`).

In the punchline, the generator source is used to send in a log sample. The punchlet is then invoked, and a
punchlet function is terminating the dag to print the result to the screen.

For example, here is the command launched for the arkoon sample:

```sh
	docker run -it --rm \
		-v ${PWD}/arkoon:/files/ \
		ghcr.io/punchplatform/punchline-java:8.1-dev \
		/files/punchline.yaml

```

Have fun !

