# Prerequisites

Developing, Building and packaging this application requires only:

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

Your custom code will be package with its dependencies as pex in docker using poetry.

## Test

As elastalert needs elasticsearch or OpenDashboard to actually works, We assume you have a Kooker up and running tu run the test.

Once your packaging stage is all good, you can easily test it using the provided example in the test directory as starting point

In this test directory you will find a complete example including example data. This example uses all the custom modules present in the repository :
- custom loader ("elastalert_modules.rule_loader.s3_loader.S3Loader")
- custom rule ("elastalert_modules.rules.suspect_name.SuspectName")
- custom enhancement ("elastalert_modules.enhancement.discover_country.DiscoverCountry")
- custom alerter ("elastalert_modules.alerter.stdout.Stdout")

to run this example simply type : 
```sh
#Include uploading step 
make run
```
Example data and rules will be upload respectively in elasticsearch and in S3. Furthermore, your builded custom artefact will be upload in the artefacts server.

```sh
#using latest-stable engine
make run ELASTALERT_IMG=ghcr.io/punchplatform/punch-elastalert:8.0-latest
```
