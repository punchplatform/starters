#!/usr/bin/env bash

: ${IMG:="ghcr.io/punchplatform/simulator:8.0-dev"}
: ${FILE:="simulator.json"}

docker run \
    --rm \
    --network=host \
    -v $(realpath ${FILE}):/punch/${FILE} ${IMG} \
    -c ${FILE} $@

exit $?