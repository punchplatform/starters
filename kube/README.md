# Introduction 

Here you can find a starter to build quickly a kube image based on [punch stormline](https://github.com/punchplatform/punch-license/pkgs/container/stormline)

# Repository layout

```sh
.
├── deploy
│   └── deployment.yaml
├── Dockerfile
├── log4j2
│   └── log4j2-stdout.xml
├── punchlets
│   └── example.punch
├── punchline
│   └── generator_print.yaml
└── README.md
```

To create your own in-built stormline, simply update your punchlet and your punchline according to the [Punch documentation](https://doc.punchplatform.com)

!!! info 

    You can also update your own logger if needed 


Then, create your docker image : 

```sh
docker build -t ghcr.io/punchplatform/punchline-starter .
```

Finally, deploy it into your Kube cluster : 

```sh
kube apply -f deploy/deployment.yaml 
```