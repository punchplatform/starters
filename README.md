# Punch Starters

This repository is a software development kit for Punch developers. It provides easy-to-start-with samples for various
use cases, implemented using java, python and punchlang functions.

The punch online documentation is available here: https://punch-1.gitbook.io/punch-doc/. The sample starters illustrates
several ways to use or configure you punchplatform.

## Punchlines

Some sample punchlines are available in all runtimes : [java](punchlines/java), [spark](punchlines/spark)
and [python](punchlines/python). Check out their guides to use them.


## Custom Docker Images

One easy way to package a punch application is to generate your own image together with the required punchlet(s) or
custom functions. Check out the [custom_images](custom_images) folder.

## Punchlets

Checkout the [punchlets](punchlines/java/punchlets) guide. Writing a punchlet is easy, testing it as easy.

## Log Parsers

A robust and industrial log parser requires one or more punchlet(s), some additional resource files, some documentation
and a way to package everything so that your parsers can be easily deployed on a production punch.

The parser SDK provides you with a maven toolkit to take care of all these packaging issues. It also provides you with a
unit test framework.

Checkout out the [parsers](parsers) guide.

## Custom Functions Development

You can write your own Java or Python functions using the punch SDK. Refer to the [custom_nodes](custom_nodes) guide.

## Simulator Tool

A simulator tool is provided to ease the day-to-day working with the punch.  
To include these in your path type in:

```sh
. activate.sh
```

You can now inject logs by simply calling `simulate.sh` from anywhere in the starters' repository.
