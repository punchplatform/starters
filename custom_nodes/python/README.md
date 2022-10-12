# Prerequisites

For development phase, you should have:

- Docker installed locally
- A reachable Kubernetes cluster

Building and packaging this application requires only:

- bash
- make
- docker
- envsubst
- zip

# Considerations

## Note

Changing *metadata.yml* information requires updating variables define in

[INFO](./INFO) :

- GROUP_ID
- ARTIFACT_ID
- VERSION

In case you desire to include your project in a CI, these variables can be updated inline by passing them as argument:

```sh
make GROUP_ID="id" VERSION="unstable" build
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
make build # check target directory
```

**Generating** poetry.lock using venv

```sh
python3 -m venv .venv-poetry
source .venv-poetry/bin/activate
poetry add requests
```

### You are a user

Building the zip archive

```sh
make docker-build
```

## Start your punchline in development mode with Docker

To test the punchline above in foreground mode simply run :

```sh
# using latest-dev engine
make run
# using latest-stable engine
make run PUNCHLINE_IMG=ghcr.io/punchplatform/punchline-python:8.0-latest
```

### Using commands

An zip archive is generated as `target/punchline-python-starter-kit-1.0.0.zip`.

Upload the archive on the Artifact Server:

```sh
make upload ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f test/punchline.yaml
```
