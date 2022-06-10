# Using Java Punchline Custom Image

This sample illustrates how you can build your own punchline image to generate a ready-to-be-launched image.

A typical log parsing punchline requires you to provides a few resource files such as:
* **the punchline yaml configuration file** itself, defining the graph of connectors and functions you want
* **the log parsers**, also called *punchlets* because they are coded using the punchlang programming language.
* **additional resource files** used (say) for enrichment, regex pattern matching etc.

You start from the
punchplatform [punchline-java](https://github.com/punchplatform/punch-license/pkgs/container/punchline-java) image. It
provides the main application, i.e. the runtime engine you need.

In the rest of this document we explain how you can add your resources as part of your own custom image.

## Repository layout

The layout of this sample is as follows:

```
├── deploy
│    └── deployment.yaml
├── log4j2
│    └── log4j2-stdout.xml
├── punchlets
│    └── print.punch
├── punchline
│    └── punchline.yaml
├── Dockerfile
└── README.md
```

To create your own custom punchline image, we suggest you first try with the examples as provided:

* The [`punchline.yaml`](punchline/punchline.yaml) is an extra simple punchline that generates some
  sample data using the generator source.
* The [`print.punch`](punchlets/print.punch) punchlet simply print the generated data.

Once clear to you, checkout the [Punch documentation](https://punch-1.gitbook.io/punch-doc/) to adapt the punchlet(s)
to your needs.

Do not hesitate to tune the [`log4j2-stdout.xml`](log4j2/log4j2-stdout.xml) file should you need more logs to understand
what is going on.

## Run the Sample

First, create your docker image :

```sh
docker build -t punchline-starter:dev .
```

Then, either run it as a simple docker application:

```sh
docker run -it punchline-starter:dev
```

Or run it into your Kubernetes cluster (make sure to have your image loaded into your kubernetes cluster registry) :

```sh
kubectl apply -f deploy/deployment.yaml 
```

You can run all this commands using the provided `Makefile` :

```sh
make build
make run
make apply

# Or simply
make all
```
