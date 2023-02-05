# Punch Detection Rule Starter

Quickstart example to create your detection rules artifact. 

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
├── src
│   ├── main
│   │   └── com
│   │       └── thalesgroup
│   │           └── punchplatform
│   │               └── sigmarule-sample
│   │                   └── network
│   │                       └── dns
│   │                           ├── mapping_dns.yml
│   │                           ├── mapping_global.yml
│   │                           └── net_dns_susp_txt_exec_strings.yml
│   └── test
│       └── punchline
│           └── punchline.yaml
└── target
    ├── data
    │   └── com
    │       └── thalesgroup
    │           └── punchplatform
    │               └── sigmarule-sample
    │                   └── network
    │                       └── dns
    │                           ├── net_dns_susp_txt_exec_strings.punch
    │                           └── net_dns_susp_txt_exec_strings.yml
    ├── sigmarule-sample-1.0.0-artifact.zip
    └── tmp
        ├── metadata.yml
        └── sigmarule-sample-1.0.0.zip
```

## Create Your Detection Rules artifact

```sh
make
```

## Deploy Your Artifact

Once your artifact is ready and packaged, you can simply refer to it in any punchline.

### Deploy To Local

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