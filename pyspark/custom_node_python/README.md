# Prerequisite

-   A quick look of our API documentation: `https://doc.punchplatform.com/doc/punchplatform-pyspark/html/index.html`
-   Brainstorm your use-case with our existing nodes: `https://doc.punchplatform.com/Reference_Guide/Data_Analytics/Nodes/Elastic_Input.html`
-   A 6.1+ punchplatform release
-   $PUNCHPLATFORM_ANALYTICS_INSTALL_DIR must be defined

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

- We expect that on your platform python is a symlink to python3.
- We highly advice you to use some python manager like `pyenv` for development phase !

**Note 3**

We provided a template hierarchy for setuptools to work out of the box; you are free to change it as to meet your requirements; just keep in mind that it should follows PEP convention for PEX packaging to work !

# Quick Start

Template hierarchy:

```sh
├── full_job.punchline
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

## Step one

```sh
# get all available commands
make
```

## Step two (Packaging)

An example node is given in `nodes` package which contains `my_use_case` module with a sub-module `my_node`

```sh
# after coding your node
make inspect path=nodes/

# build a pex distribution
make package name=complex_algorithm_dependencies.pex
```

## Step three (Installation)

```sh
punchpkg analytics install-pex dist/complex_algorithm_dependencies.pex
```

## Step four (Execute to test)

```sh
punchlinectl start -p full_job.punchline -r pyspark
```
