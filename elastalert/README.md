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

Your custom code will be packaged with its dependencies as pex in docker using poetry.

## Test

## Run on kooker 
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
Example data and rules will be uploaded respectively in elasticsearch and in S3. Furthermore, your built custom artefact will be uploaded in the artifact server.

Finally, It will consume the kafka topic (i.e. where the alert should be written) and print it on your console



### Run on local

To build the package locally :
```sh
make build
```

This step assumes you have an elasticsearch and S3 locally. 

Run `config_basic.yaml` :
```sh

PEX_SCRIPT=elastalert dist/punchplatform-elastalert-core.pex --config resources/config/local/config_basic.yaml
```
