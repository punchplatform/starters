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
make run ENGINE_IMG=ghcr.io/punchplatform/punchline-pyspark:8.0-latest
```

### Using commands

An zip archive is generated as `target/punchline-pyspark-starter-kit-1.0.0.zip`.

Upload the archive on the Artifact Server:

```sh
make upload-artifact ARTIFACT_SERVER_UPLOAD_URL="http://artifacts-server.kooker:4245/v1/artifacts/upload"
```

Start your punchline on Kubernetes :
```sh
kubectl apply -f punchline.yaml
```

//////////////////////////

### Step 2

Now let's inject some fake data into elasticsearch

```sh
# Fake Data
PUT /customers-2020.09.23/_doc/1
{
  "name": "Johnny Halll",
  "@timestamp": "2020-09-23T14:47:02.448Z"
}

PUT /customers-2020.09.23/_doc/2
{
  "name": "Jack Mao",
  "@timestamp": "2020-09-24T13:18:02.448Z"
}

PUT /customers-2020.09.23/_doc/3
{
  "name": "Jack Mao",
  "@timestamp": "2020-09-23T15:47:02.448Z"
}

PUT /customers-2020.09.23/_doc/4
{
  "name": "Jack Maoaaa",
  "@timestamp": "2020-09-23T21:52:02.448Z"
}
```

We are going to insert some rules in index `myrule`

```sh
# Expected Rule in JSON

PUT /myrule/_doc/1
{
  "name": "Es rule stdout",
  "@timestamp": "2020-09-23T14:47:02.448Z",
  "es_host": "localhost",
  "es_port": 9200,
  "index": "customers-*",
  "filter": [],
  "type": "elastalert_core.rules.suspect_name.SuspectName",
  "suspect_names": ["Jeff Meza", "Chritiano Ronaldo", "Jack Mao"],
  "match_enhancements": ["elastalert_core.enhancement.discover_country.DiscoverCountry"],
  "alert": "elastalert_modules.alerter.stdout.Stdout", # additionnal elastalert modules
  "stdout_command": true,
  "start": "2020-09-23T14:47:02.448Z",
  "hash": "12345678910"
}

PUT /myrule/_doc/2
{
  "name": "Es rule kafka",
  "@timestamp": "2020-09-23T14:47:02.448Z",
  "es_host": "localhost",
  "es_port": 9200,
  "index": "customers-*",
  "filter": [],
  "type": "elastalert_core.rules.suspect_name.SuspectName",
  "suspect_names": ["Jeff Meza", "Chritiano Ronaldo", "Jack Mao"],
  "match_enhancements": ["elastalert_core.enhancement.discover_country.DiscoverCountry"],
  "alert": "elastalert_core.alerter.kafka_alerter.KafkaAlerter",
  "topic": "jonathan",
  "bootstrap_servers": ["localhost:9092"],
  "close_timeout": 10,
  "flush_timeout": 10
}


### Step 3

Now we can test by executing the command below:

```sh
PEX_SCRIPT=elastalert ./dist/bin/elastalert-* --config example/config_rule_loader.yaml --start 2020-09-23T14:47:02.448Z