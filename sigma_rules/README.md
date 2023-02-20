# Punch Sigma Rules Starter

Quickstart example to create your Sigma Rules artifact. 

## Layout

The layout is straightfowrward:

```
.
├── INFO
├── LICENSE
├── Makefile
├── metadata
│   └── metadata.yml
├── README.md
└── src
    ├── main
    │   └── network
    │       └── dns
    │           ├── mapping_dns.yml
    │           ├── mapping_global.yml
    │           └── net_dns_susp_txt_exec_strings.yml
    └── test
        └── punchline
            └── punchline.yaml
```

Where:
* `com/github/punchplatform` is the group identifier.
* `sigmarule` is the artifact identifier.

You can put your rules in `src/main` with the file structure you want. The same file tree will be created in the artifact.  
You must put at least one mapping file per folder containing rules, but you can put more than one. To be considered mapping files, they must contain the keyword `mapping` in the file name.

Tests are provided in the `src/test` folder. In there:
* `punchline` folder simply provides a sample punchline that illustrate how to use your Sigma Rules.
This file is typically helpful for the support or customer in charge of deploying your Sigma Rules to his production platform.

## Create Your Sigma Rules Artifact

To convert your Sigma rules to Punchlang and package them into an artifact just run the bellow command.

```sh
make
```

## Deploy Your Artifact

Once your artifact is ready and packaged, you can simply refer to it in any punchline.

### Deploy To Local Maven Repository

```sh
make local-install
```

### Deploy To Kubernetes

First, deliver your parser to the target kubernetes cluster artifact registry. 
If you do not know what that is, we advise you to use [kooker](https://github.com/punchplatform/kooker).
Kooker is an small open source project to bootstrap a complete K8 cluster automatically equipped with the
additional punch service, including the artifact server. 

Once kooker is started; simply type in:

```sh
make upload
```