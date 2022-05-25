# Prerequisites

For development phase, you should have:

- Docker install locally and an access to a Kubernetes cluster

Building and packaging this application requires only:

- bash
- make
- docker
- envsubst
- zip

# Considerations

## Note 1

Renaming the root source python module from `custom_node` to `something_else`

Requires updating in [INFO](./INFO) **SOURCES** variable to `something_else`

## Note 2

Likewise as in *Note 1*, changing *metadata.yml* information requires updating variables define in

[INFO](./INFO) :

- GROUP_ID
- ARTIFACT_ID
- VERSION

In case you desire to include your project in a CI, these variables can be updated inline by passing them as argument:

```sh
make GROUP_ID="id" VERSION="unstable" artifact
```

# Quick Start

```sh
# Read the doc
make help
```

## Package

### You are a developer

```sh
# you are required to have python3 installed and configured
make artifact # check target directory
```

**Generating** poetry.lock using venv

```sh
make venv
source .venv-poetry/bin/activate
poetry add requests
```

**Generating** poetry.lock using docker

**Usage**

```sh
# In terminal 1
# activate docker-poetry
make docker-poetry

# add dependencies
poetry add requests

# In terminal 2
make docker-poetry-commit

# When finished
# In terminal 1
CTRL + D

# Start a new build
make docker-build

# test
make run
```

**During development phase**

```sh
make lint # abuse it ! this will tidy your code and reveals potential bugs...
```

### You are a user

Building the zip archive using your desired python version

```sh
make docker-build PYTHON_VERSION_TAG=3.10.4-slim
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run :

```sh
# using latest-dev engine
make run
# using latest-stable engine
make run ENGINE_IMG=ghcr.io/punchplatform/punchline-python:8.0-latest
```

### Using commands

An zip archive is generated as `target/punchline-python-starter-kit-1.0.0.zip`.

Upload the archive on the Artifact Server:

```sh
make upload-artifact ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f punchline.yaml
```
