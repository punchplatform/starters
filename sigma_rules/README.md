# Punch Sigma Rule Starter

Quickstart example to create your sigmarule artifact. 

## Layout

The layout for detection rules is straightfowrward:

```
.
├── INFO
├── LICENSE
├── Makefile
├── metadata
│   └── metadata.yml
├── README.md
└── src
    ├── main
    │   └── com
    │       └── github
    │           └── punchplatform
    │               └── sigmarule
    │                   └── network
    │                       └── dns
    │                           ├── mapping_dns.yml
    │                           ├── mapping_global.yml
    │                           └── net_dns_susp_txt_exec_strings.yml
    └── test
        └── punchline
            └── punchline.yaml
```

Where:
* `com/github/punchplatform` is the group identifier.
* `sigmarule` is the artifact identifier.

You can put your rules in the `src/main/group_id/artifact_id` folder in any folder tree. The same tree will be produced in the artifact.  
You also need to put mapping files at same level as the rules with `mapping` key in the file names. Note that you can put multiple mapping files.

Tests are provided in the `src/test` folder. In there:
* `punchline` folder simply provides a sample punchline that illustrate how to deploy your sigmarule artifact.
This file is typically helpful for the support or customer in charge of deploying your sigmarule to his production platform.

## Create Your Sigmarule Artifact

To convert your rule to Punchlang and package them into an artifact just run the bellow command.

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