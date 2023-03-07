# Punch Sigma Rules Starter

Quickstart example to create your sigma rules artifact. 

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
    │   ├── pipelines
    │   │   └── network
    │   │       └── dns
    │   │           ├── mapping_dns.yml
    │   │           └── mapping_global.yml
    │   └── rules
    │       └── network
    │           └── dns
    │               └── net_dns_susp_txt_exec_strings.yml
    └── test
        └── punchline
            └── punchline.yaml
```

Source are provided in the `src/main` folder. In there:
* You can put your rules in `rules` with the file structure you want.  
* You can put your pipelines in `pipelines` with the same file structure as rules.  

Tests are provided in the `src/test` folder. In there:
* `punchline` folder simply provides a sample punchline that illustrate how to use your sigma rules.
This file is typically helpful for the support or customer in charge of deploying your sigma rules to his production platform.

## Create Your Sigma Rules Artifact

To package your sigma rules to an artifact just run the bellow command.

```sh
make
```

## Deploy Your Artifact

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

## How to use

Once your artifact is deployed, you can simply refer to it in any punchline using the following configuration:

```yaml
    - id: alert
      kind: function
      type: sigma_rule
      settings:
        rules:
          - punch-sigma-rules:com.github.punchplatform:sample:1.0.0
        punchlets:
          - "{ ; }"
```