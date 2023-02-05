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
.
├── INFO
├── Makefile
├── README.md
├── metadata
│   └── metadata.yml
├── src
│   ├── main
│   │   └── punchlang
│   │       └── com
│   │           └── analytics
│   │               └── web
│   │                   └── apache
│   │                       ├── enrichment.punch
│   │                       ├── groks2
│   │                       │   └── parser.grok
│   │                       ├── http_codes.json
│   │                       └── parser.punch
│   └── test
│       ├── puncher
│       │   └── unit.yaml
│       └── punchline
│           └── punchline.yaml
└── target
    ├── apache-3.0.0-artifact.zip
    └── tmp
        ├── apache-3.0.0.zip
        └── metadata.yml
```

Where:

* 'src/main/punchlang' is the root folder containing your parser, patterns and resource files. 
* `parser.punch` and `enrichment.punch` are sample punchlets. They illustrate the basics. This is where you
  write the actual logic of your log parsing.
* `groks2/pattern.grok` is a sample grok pattern. The punch comes with many patterns directly loaded, but here is how you
  can add your own.
* `http_codes.json` is a sample resource files. Check how it is used in the enrichment.punch punchlet.

Tests are provided in the 'src/test' folder. In there:
* puncher is the root folder for unit test executed using the punch provided puncher tool.
* punchline is optional. It simply provides a sample punchline that illustrate how to deploy yoru parser. This file is typically helpful for the support or customer in charge of deploying your parser to his production platform.

You must conform to the src/main/punchlang and src/test/puncher root folders. 

## Test Your Parsers

To test your parsers with your unit tests file simply run :
```sh
make test
```

It will launch a punch tool called `puncher` in a docker container which is in charge of running your tests. Be sure to
have docker installed.

## Understand Punch Parsers Archives

Here is the logic. First note that this layout is designed to hold one or several group of punchlets. Each 'parser' is a
set of punchlets, groks and resources that take care of some data transformation, normalization and enrichment.

You are free to create one parser project per log type of vendor (i.e. cisco, sourcefire, linux etc..). The only
drawback is that you might end up with many projects. Hence, the facility to deal with a single project to hold several
of your parsers.

Let us consider an example. First let us describe how you will refer to your punchlets in data pipeline. If you install
the above com.analytics.web:3.0.0 package to a punchplatform, you will be able to refer to

```sh
  com.analytics.web:3.0.0:com/analytics/web/apache/parser.punch
```

in your data pipelines. As simple as that. That basic mechanism ensure your parsers will be worldwide unique.

Coming back to the grouping of several punchlets as part of a parser, and several parsers as part of parser archive here
is the rationale: the punch is designed to help you write modular functions. You can provide generic functions (say to
deal with the very first syslog header of your logs or to enrich your logs with geoip data)
that you might want to execute in front of after every other vendor specific function. You typically would like to have
the following chain of functions applied to each incoming 'cisco' log:

```sh
  com/analytics/common/header.punch
  com/analytics/cisco/parser.punch
  com/analytics/cisco/enrich.punch
  com/analytics/common/geoip.punch
```

To achieve that you can package in your parser archive two sub-parsers: one 'common', and one 'cisco'.

Whatever you decide to do, each parser (in the above example  'cisco or 'common')
is defined using a [INFO](./INFO) file.

That file defines the essential information about the parser. Here the sample MANIFEST.yaml generated above:

```yaml
apiVersion: 1.0
GROUP_ID ?= com.analytics.web
ARTIFACT_ID ?= apache
VERSION ?= 3.0.0
BUILD_TIMESTAMP = $(shell date '+%Y-%m-%d %H:%M')
ARTIFACT_SERVER_URL ?= http://artifacts-server.kooker:4245
```

## Using Your Parsers

Once your parser is ready, you can simply refer to them in your punchline. Remember a punchline is a log processing pipeline where you chain your parser. Check out the [punchline example](src/test/punchline/punchline.yaml).

On a production platform, you lust yet upload your parser to make it available to punchlines. That is achieved by publishing the packaged parser to Punch Artifacts Server. If you have kooker on your laptop you can type in

```sh
make upload
```

See the section above for various option to play with a kubernetes cluster. 

## HowTos Use with Kubernetes or Kooker

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
cat simulator.json | kubectl run -i simulator --image ghcr.io/punchplatform/simulator:8.1-dev -- -c - --host my-parser-input.default
```

Note: When you want to stop your simulator session, a '<ctrl>-c' will not be enough (it will just detach your console
from the remote pod running in Kubernetes). You will have to remove your simulator using:
```sh
kubectl delete pod simulator
```

