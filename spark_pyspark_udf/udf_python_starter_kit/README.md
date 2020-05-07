# Introduction

This starter is designed to give you the necessary commands and information on how to
integrate custom pyspark udf in your punchline.

## Directory structure

├── README.md
├── setup.py (define here your needed requirements)
└── udf_example (your package name)
    ├── __init__.py
    └── udf0_example.py (script containing your udf function)

## How to use in punchline ?

```sh
# in your punchline configuration of your sql_node
{
    type: sql
    component: sql
    settings: {
        register_udf: [
            {
                function_name: myOwnFunc2
                function_definition: udf_example.udf0_example.test_random
                schema_ddl: Integer
            }
        ]
        statement_list: [
            {
                statement: SELECT myOwnFunc2() FROM input_data
                output_table_name: data
            }
        ]
    }
    publish: [
        {
            stream: data
        }
    ]
    subscribe: [
        {
            stream: data
            component: input
        }
    ]
}
```

## custom requirements

Custom requirements can be added in install_requires list as below:

```python
from setuptools import setup

setup (
  name = "udf_examples",
  packages=["udf_example"],
  install_requires=[
    "elasticsearch==7.0.5",
    "redis",
  ]
)
```

# Quick Start

```sh
# generate a udf.pex that contains all your python code and requirements
pex . -o udf.pex

# install the udf.pex dependency like other dependencies
punchpkg pyspark install-dependencies udf.pex

# launch the example
punchlinectl start -p example_pyspark_udf.pml

# output

Show node result:
+--------------+----------------------------------------+
| myOwnFunc2() | UDF:punch_str_to_array_double(Message) |
+--------------+----------------------------------------+
|      6       |            [1.0, 2.0, 3.0]             |
|      6       |          [1.0, 2.0, 3.0, 5.0]          |
|      6       |       [1.0, 2.0, 3.0, 99.0, 5.0]       |
|      6       |            [0.3, 2.0, 3.0]             |
+--------------+----------------------------------------+
```
