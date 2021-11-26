# Prerequisite

-   A quick look of our API documentation: `https://doc.punchplatform.com/doc/punchplatform-pyspark/html/index.html`
-   Brainstorm your use-case with our existing nodes: `https://doc.punchplatform.com/Reference_Guide/Data_Analytics/Nodes/Elastic_Input.html`
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
- We highly advice you to use some python manager like `pyenv` for development phase !
- In case you want to use shiva for launching pyspark punchlines; make sure that python3.6 is installed (not pyenv).

**Note 3**

A template hierarchy for setuptools to work out of the box is provided; you are free to change it so as to meet your requirements; just keep in mind that it should follows PEP convention for PEX packaging to work !

# Quick Start

Template hierarchy:

```sh
├── full_job.yaml
├── Makefile
├── nodes
│   ├── __init__.py
│   └── my_use_case
│       ├── __init__.py
│       └── my_node
│           ├── complex_algorithm.py
│           └── __init__.py
├── README.md
└── setup.py
```

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode

Import your node : 

```sh
ROOT=$(pwd)  # directory of this README.md
cp $ROOT/target/punch-spark-python-node-starter-kit-1.0.0.pex $PUNCHPLATFORM_INSTALL_DIR/extlib/pyspark/
```

Start your punchline in foreground mode :  

```sh
punchlinectl start -p $ROOT/full_job.yaml
```

## Start your punchline in production mode

Import your artefact : 

```sh
resourcectl --url $ARTEFACT_URL upload --files target/punch-spark-python-node-starter-kit-1.0.0-artefact.zip
```
Start your punchline on kubernetes 

```sh
kubectl apply -f $ROOT/full_job.yaml
```
