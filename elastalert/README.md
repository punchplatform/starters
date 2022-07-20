# Prerequisites

Developing, Building and packaging this application requires only:

- Docker
- bash
- make
- docker
- envsubst
- zip

# Considerations

Likewise as in *Note 1*, changing *metadata.yml* information requires updating variables define in

[INFO](./INFO) :

- GROUP_ID
- ARTIFACT_ID
- VERSION

In case you desire to include your project in a CI, these variables can be updated inline by passing them as argument:

```sh
make GROUP_ID="id" VERSION="unstable" docker-build
```

# Quick Start

```sh
# Read the doc
make help
```

## Package

```sh
# you are required to have docker installed 
make docker-build 
```

**Generating** poetry.lock using docker

**Usage**

```sh
# In terminal 1
# activate docker-poetry
make docker-poetry  # A CHANGER

# add dependencies
poetry add requests

# In terminal 2
make docker-poetry-commit

# When finished
# In terminal 1
CTRL + D

# Start a new build
make docker-build
```

## Test

As elastalert needs elasticsearch or OpenDashboard to actually works, We assume you have a Kooker up and running tu run the test.

```sh
#Include uploading step 
make run-kooker
```

```sh
***using latest-stable engine**
make run-kooker ENGINE_IMG=ghcr.io/punchplatform/punch-elastalert:8.0-latest
```
