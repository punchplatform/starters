# Prerequisite

- A Kubernetes cluster reachable
- Punch Services (Operator & Artefact Server) deployed
- Clone this repository

# Notes

A `Makefile` is put at your disposal, use it to clean, lint and format your custom node code !

```sh
# check lint and code formatting for module algorithms
# in general; you should launch this command before any commit
make inspect path=nodes/

# clean unwanted .pyc if any or other python related binaries during runtime execution
make clean
```

**Note 1**

If you are using an IDE like Pycharm (or similar); configure your IDE to use the generated `.venv` from this directory.

**Note 2**

- We expect that on your platform python is a symlink to python3.6.x
- We highly advise you to use some python manager like `pyenv` for development phase !
- In case you want to use shiva for launching pyspark punchlines; make sure that python3.6 is installed (not pyenv).

**Note 3**

A template hierarchy for `setuptools` to work out of the box is provided; you are free to change it to meet your
requirements; just keep in mind that it should follow PEP convention for PEX packaging to work !

# Quick Start

Template hierarchy:

```sh
├── full_job.yaml
├── Makefile
├── nodes
│   ├── __init__.py
│   └── my_use_case
│       ├── __init__.py
│       └── my_node
│           ├── complex_algorithm.py
│           ├── complex_algorithm_pre_execution.py
│           ├── complex_algorithm_post_execution.py
│           └── __init__.py
├── README.md
└── setup.py
```

## Build

```sh
mvn clean install
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run :

```sh
docker run --rm --entrypoint sparkctl-client \
    -v $PWD/full_job.yaml:/usr/share/punch/full_job.yml \
    -v $PWD/target/punch-spark-python-node-starter-kit-1.0.0.pex:/usr/share/punch/extlib/punch-spark-python-node-starter-kit-1.0.0.pex \
    ghcr.io/punchplatform/punchline-spark:8.0-dev --job /usr/share/punch/full_job.yml
```

## Start your punchline in production mode with Kubernetes

Maven generates `./target/punchline-java-starter-kit-1.0.0-artifact.zip`.

You simply have to upload it to the Punch Artifacts Server using this command :
```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/punchline-java-starter-kit-1.0.0-artifact.zip -F override=true
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f full_job.yaml
```
