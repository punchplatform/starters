# Punch Starters

This repository is a software development kit for Punch developers. It provides easy-to-start-with samples for various
use cases, implemented using java, python and punchlang functions.

The punch online documentation is available here: https://doc.punchplatform.com. 

The starter examples use either docker or a kubernetes cluster equipped with the
punch services. 

With docker you only need docker to be installed on your laptop. 
If your work with a target kubernetes cluster, make sure you installed 
'kubectl' and have a valid '.kube/config' file. 
 
## Punchlines

Some sample punchlines are available in all runtimes : [java](punchlines/java), [spark](punchlines/spark)
and [python](punchlines/python). Check out their guides to use them.

## Custom Docker Images

One easy way to package a punch application is to generate your own image together with the required punchlet(s) or
custom functions. Check out the [custom_images](custom_images) folder.

## Punchlets

Punchlets are small arbitrary functions written using the punchlang language. 
Checkout the [punchlets](punchlines/java/punchlets) guide. Writing a punchlet is easy, testing it as easy.

## Log Parser

A robust and industrial log parser requires one or more punchlet(s), some additional resource files, some documentation
and a way to package everything so that your parsers can be easily deployed on a production punch.

The parser SDK provides you with a toolkit to handle of all these packaging issues. It also provides you with a
unit test framework.

Checkout out the sample [parser](parser) folder.

## Sigma Detection Rules

Checkout out the sample [sigma_rule](sigma_rules) folder.

## Model

Mlflow model that can be used in a punchline

Checkout out the [model](model) guide.

## Custom Functions Development

You can write your own Java or Python functions using the punch SDK. Refer to the [custom_nodes](custom_nodes) guide.

## Simulator Tool

A simulator tool is provided to ease the day-to-day working with the punch.  
To include these in your path type in:

```sh
. activate.sh
```

You can now inject logs by simply executing `simulate.sh` from anywhere in the starters' repository.
