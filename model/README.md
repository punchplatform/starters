# Model Starter

Quickstart example to create your model artifact. 

## Basics

To create model artifact, clone or download this repository, you will get the following layout:

```
└── sample
    └── model.mlflow
```

Where:

* `model.mlflow` is your model file.

## Using Your Model

Once your model is ready, you can simply refer to them in your punchline.

Check out the [punchline example](test/punchline.yaml).

## Run in foreground with docker

To test the example punchline in foreground mode simply run :

```sh
make run
```

or

```sh
docker run -it \
    -v $PWD/sample:/usr/share/punch/artifacts/io/github/starter/model/1.0.0 \
    -v $PWD/punchline.yaml:/data/punchline.yaml \
    --network=host \
    ghcr.io/punchplatform/punchline-pyspark:8.0-dev \
    /data/punchline.yaml
```

## Start your punchline in production mode on Kubernetes

A zip archive containing your model and a metadata file can be build using make : `model-1.0.0-artifact.zip`

### Using Makefile

You can apply your punchline, start a service and run the simulator by running :
```sh
make apply
```

When the simulator starts, you can exit this command using `Ctrl+c` and run :
```sh
make logs
```

This should show your punchline logs.

To delete everything, simply run :
```sh
make delete
```

### Using shell commands

You simply have to upload it to the Punch Artefact Server using this command (do not forget to update the artifact
service name):

```sh
curl -X POST "http://artifacts-server.kooker:4245/v1/artifacts/upload" -F artifact=@target/model-1.0.0-artifact.zip -F override=true
```

Start your punchline on kubernetes, de not forget to check the artifact service name in `punchline.yaml` file before
executing this command :

```sh
kubectl apply -f punchline.yaml
```

In one window, view your punchline log :

```sh
kubectl logs -f --tail -1 -l punchline-name=punch-model
```
