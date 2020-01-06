# Punch Airflow starter kit

The goal of this repository is to provide a quick start boilerplate for you to test airflow within the punchplatform.

## Prerequisite 

- Punchplatform standalone 6.x or later
- Python3.6+
- punchpkg available in $PATH

## Quick Start

```sh
# Use punchpkg airflow info to get install dir of airflow

    >   punchpkg airflow info

# Let's try running our airflow dag

# To begin, we enable punchpkg autocompletion when <TAB><TAB> is being used

    >  eval "$(_punchpkg_COMPLETE=source punchpkg)" # for auto completion
    > punchpkg airflow install-dags $(pwd)/dags  # $(pwd) refers to this README root directory
    > punchpkg airflow install-resources $(pwd)/resources  # $(pwd) refers to this README root directory

# Visualize our dag in airflow

    > Go to airflow webserver ui (http://localhost:8089)
    > Select your dag and run it !
```

## Punch airflow explained

```sh
# Preview of this directory structure
├── dags
│   ├── dag_template
│   │   ├── __init__.py
│   │   ├── job_info.py
│   │   └── my_dag.py
│   └── __init__.py
├── README.md
└── resources
    ├── generate_me_first.pl
    └── generate_me_second.pl

# As you may have notice, this repository takes in the same repository structure as the one provided upon installing
# a standalone version of 6.x

# dags directory (which is in fact a python module) should contain as many python sub-modules as dags you want to install within
# our airflow standalone. For instance, here we want to install our dag_template sub-module.

# Each dags directory should contain the following files:

1) __init__.py
2) job_info.py
3) name_of_your_dag.py
4) one or more resource files located in $(pwd)/resources

# job_info.py

    > contains the needed meta data to launch an airflow dag
    > contains a plan key which is used during templating of punchline configuration file

# name_of_your_dag.py

    > your airflow dag definition, i.e which configuration file should be executed first and at what time, etc...

```
