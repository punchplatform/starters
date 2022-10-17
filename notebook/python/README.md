# Notebook node example
This example shows how to execute a notebook in a python punchline. 

**Have a look at the notebook located in *notebook/* folder and the punchline in *test/punchline.yaml***  
This notebook expects to receive data in table1 and table2 variables. We generate these inputs with the generator source node and redirect the tables to the notebook node. Only parameters defined in the cell starting by "#parameters" can be overridden in the punchline. Then we get back the new dataframe (new_table variable) created by the notebook into the table output and print it. 

This notebook need numpy library loaded via a pex file.

```sh
# Read the doc
make help
```
## Prerequisites

You should have:

- Docker install locally and an access to a Kubernetes cluster

Building and packaging this application requires only:

- bash
- make
- docker
- sed
- zip

## Packaging

### Pex artifact
Create a pex with libraries listed in the *pex/requirements.txt* file and upload it in artifact server.
```sh
make build-pex upload-pex
```

### Notebook artifact
Create an artifact of type *file* containing the notebook and upload it in artifact server.
```sh
make build-notebook upload-notebook
```
## Running


### Start your punchline with Docker

To test the punchline above in foreground mode simply run :

```sh
make run
```
### Start your punchline on Kubernetes

```sh
make apply
```

```sh
make log
```
