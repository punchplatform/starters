# Simulator

Punch simulator is a powerfull tool that generates logs or various textual data, and send
it to a target destination. Punch dev, support teams and customers uses it to emulate various types of logs.

To use it you simply define a JSON files that controls how you generate your data and where
to send it. An example is provided here ([simulator.json](simulator.json). To see it in action simply type in

```sh
./simulates.sh --dump
````

The --dump option simply print the generated logs to stdout. 

## Simple example

Try it now on a simple punchline. A sample punchline  is provided in [punchline_testit.yaml](./testit/punchline_testit.yaml). It simply accepts incomings logs and print them to stdout. 

Run the punchline first: 

```sh
make testit
````
Then (from another terminal) start the simulator. We use the -t option to limit the traffic to one per second.
```sh
./simulates.sh -t 1
````

## Advanced log collection example

The punchlines provided in the [log_collection](./log_collection) folder illustrate a 
complete four hops setup used to collect logs from a remote site and safely transfer
them to a central platform: 

* [remote_collector.yaml](./log_collection/remote_collector.yaml) : grabs the log from the remote site and store them in a local kafka cluster
* [remote_forwarder.yaml](./log_collection/remote_forwarder.yaml) : consumes that local kafka cluster and forward the logs to the central site receiver.
* [central_receiver.yaml](./log_collection/central_receiver.yaml) : runs on a central site, it receives the logs from remote collectors  and store them in a kafka broker.
* [central_indexer.yaml](./log_collection/central_indexer.yaml) : is the last hop, it read the cenral site kafka broker, parse the logs and index them in elasticsearch.

### Commands

To run this you need a K8 cluster with kafka and elasticsearch. Checkout the kooker project if you do not have that yet. 

Simply execute the following commands. Note that all resources will be created in mytenant namespace.

```sh
# use kubectl port-forward
kubectl port-forward remote_**** 8880

# run all punchlines and services
kubectl apply -f punchlines

# bgenerate and send the logs to the remote colector. 
./simulate.sh
```