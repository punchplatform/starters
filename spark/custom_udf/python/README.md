# Introduction

This starter is designed to give you the necessary commands and information on how to
integrate custom pyspark udf in your punchline.

## Directory structure

```sh
├── README.md
├── setup.py (define here your needed requirements)
└── udf_example (your package name)
    ├── __init__.py
    └── udf0_example.py (script containing your udf function)
```

## How to use in punchline ?

```sh
# in your punchline configuration of your sql_node
- type: sql
      component: processor
      settings:
        register_udf:
          - function_name: myOwnFunc2
            function_definition: udf_example.udf0_example.test_random
            schema_ddl: Integer
        statement_list:
          - output_table_name: data
            statement: SELECT myOwnFunc2(), punch_str_to_array_double(Message) FROM input_data
      subscribe:
      - component: dataset_generator
        stream: data      
      publish:
      - stream: data  
```

## Custom requirements

Custom requirements can be added in install_requires list as below:

```python
from setuptools import setup

setup (
  name = "udf_example",
  packages=["udf_example"],
  install_requires=[
    "elasticsearch==7.0.5",
    "redis",
  ]
)
```

## Note

For your project, don't forget to rename `udf_example` to something more meaningful for your use case, ideally with a version number. Both name parameter from `setup.py` and folder name `udf_example` should be renamed !

# Quick Start

## Build 

```sh
mvn clean install
```

## Start your punchline in development mode

Import your node : 

```sh
ROOT=$(pwd)  # directory of this README.md
cp $ROOT/target/punchplatform-udf-python-starter-kit-1.0.0.pex $PUNCHPLATFORM_INSTALL_DIR/extlib/pyspark/
```

Start your punchline in foreground mode :  

```sh
punchlinectl start -p $ROOT/example_pyspark_udf.yaml
```

## Start your punchline in production mode

Import your artefact : 

```sh
resourcectl --url $ARTEFACT_URL upload --files target/punchplatform-udf-python-starter-kit-1.0.0-artefact.zip
```
Start your punchline on kubernetes 

```sh
kubectl apply -f $ROOT/example_pyspark_udf.yaml
```
