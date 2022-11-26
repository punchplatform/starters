# Parsers Starter

Quickstart example to create your parser development project. On the punch you develop parsers as part of maven
projects. A single project can contain on or several groups. Each group consists of :

* one or several punchlets.
* optional grok patterns
* optional resource files
* optional unit and sample test files

Together the punchlets parse, normalise and enrich the incoming logs, whatever their format is. The punch comes equipped
with many parsers. Should you write your own, this project is for you.

## Basics

To create punch parsers, clone or download this repository, you will get the following layout:

```
│── sample
│   ├── MANIFEST.yml
│   ├── groks
│   │   └── pattern.grok
│   ├── enrich.punch
│   ├── parser.punch
│   ├── resources
│   │   └── color_codes.json
│   └── test
│       ├── sample.txt
│       ├── unit_chain.json
│       └── unit_punchlet.json
└── tools
    └── test.sh
```

Where:

* 'io/github/starter/parsers/sample' is a fully qualified name of your parsers. That will be the way to uniquely identify,
  and deploy your parsers on a production punch.
* `parser.punch` and `enrich.punch` are sample punchlets. Check it out it illustrates the basics. This is where you
  write the actual logic of your log parsing or more generally data transformation.
* `groks/pattern.grok` is a sample grok pattern. The punch comes with many patterns directly loaded, but here is how you
  can add your own.
* `resources/color_code.json` is a sample resource files. In this sample it is used to add a numerical color code from a
  color string value ('red' or 'green').
* `test/unit_chain.json` and `test/unit_punchlet.json` are punch unit test files. That lets you define unit tests to
  ensure each punchlet or a sequence of punchlets behave exactly as you expect.
* `test/sample.txt` is an example a sample log file. These can be used to test a large number of logs.

## Understand Punch Parsers Archives

Here is the logic. First note that this layout is designed to hold one or several group of punchlets. Each 'parser' is a
set of punchlets, groks and resources that take care of some data transformation, normalization and enrichment.

You are free to create one parser project per log type of vendor (i.e. cisco, sourcefire, linux etc..). The only
drawback is that you might end up with many projects. Hence, the facility to deal with a single project to hold several
of your parsers.

Let us consider an example. First let us describe how you will refer to your punchlets in data pipeline. If you install
the above io.github.starter.parsers:1.0.0 package to a punchplatform, you will be able to refer to

```sh
  io/github/starter/sample/parser.punch
```

in your data pipelines. As simple as that. That basic mechanism ensure your parsers will be worldwide unique.

Coming back to the grouping of several punchlets as part of a parser, and several parsers as part of parser archive here
is the rationale: the punch is designed to help you write modular functions. You can provide generic functions (say to
deal with the very first syslog header of your logs or to enrich your logs with geoip data)
that you might want to execute in front of after every other vendor specific function. You typically would like to have
the following chain of functions applied to each incoming 'cisco' log:

```sh
  io/github/starter/common/header.punch
  io/github/starter/cisco/parser.punch
  io/github/starter/cisco/enrich.punch
  io/github/starter/common/geoip.punch
```

To achieve that you can package in your parser archive two sub-parsers: one 'common', and one 'cisco'.

Whatever you decide to do, each parser (in the above example 'sample', or 'cisco or 'common' in our last example)
is defined using a 'MANIFEST.yaml' file.

That file defines the essential information about the parser. Here the sample MANIFEST.yaml generated above:

```yaml
apiVersion: 1.0
kind: PunchParserArchive
metadata:
  name: "sample punch parser"
  labels:
    description: "a sample parser for you to easily start coding your own"
    category: sample
    author: "punch team"
    performance: 3000
    vendor: punchplatform
spec:
  punchlets:
    - parser.punch
  resources:
    - resources/color_codes.json
  groks:
    - groks/pattern.grok
```

## Test Your Parsers

To test your parsers with your unit tests file simply run :
```sh
make test
```

It will launch a punch tool called `puncher` in a docker container which is in charge of running your tests. Be sure to
have docker installed before running this command

## Using Your Parsers

Once your parser is ready, you can simply refer to them in your punchline. Remember a punchline is a log processing
pipeline where you chain your parser.

Check out the [punchline example](test/punchline.yaml).

## Run in foreground with docker

To test the example punchline in foreground mode simply run :

```sh
make run
```

or

```sh
docker run -it \
    -v $PWD/target/parsers-1.0.0.zip:/usr/share/punch/artifacts/io/github/starter/parsers/1.0.0/parsers-1.0.0.zip \
    -v $PWD/punchline.yaml:/data/punchline.yaml \
    --network=host \
    ghcr.io/punchplatform/punchline-java:8.0-dev \
    /data/punchline.yaml
```

And in another terminal inject some data :

```sh
(cd ../simulator && ./simulate.sh)
```

## Start your punchline in production mode on Kubernetes

A zip archive containing your parsers and a metadata file can be build using maven : `parsers-1.0.0-artifact.zip`

### Using Makefile

You can apply your punchline, start a service and run the simulator by running :
```sh
make apply
```

When the simulator starts, you can exit this command using `Ctrl+c` and run :
```sh
make logs
```

This should show your punchline logs.

To delete everything, simply run :
```sh
make delete
```

### Using shell commands

You simply have to upload it to the Punch Artefact Server using this command (do not forget to update the artifact
service name):

```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/parsers-1.0.0-artifact.zip -F override=true
```

Start your punchline on kubernetes, de not forget to check the artifact service name in `punchline.yaml` file before
executing this command :

```sh
kubectl apply -f punchline.yaml
```

In one window, view your punchline log :

```sh
kubectl logs -f --tail -1 -l punchline-name=my-parser
```

In another one, inject logs to your punchline:

```sh
# This will just create a target service name, to be able to send logs to our punchline
kubectl apply -f my_service.yaml  

# Then we start a simulator inside the kubernetes cluster :
cat simulator.json | kubectl run -i simulator --image ghcr.io/punchplatform/simulator:8.0-dev -- -c - --host my-parser-input.default
```

Note: When you want to stop your simulator session, a '<ctrl>-c' will not be enough (it will just detach your console
from the remote pod running in Kubernetes). You will have to remove your simulator using:
```sh
kubectl delete pod simulator
```

