#!/bin/bash -ue


# The purpose of this is to include some useful tools in your path
# juste source it:
# source ./activate.sh

[ "$BASH_SOURCE" != "" ] || { echo "This file is supposed to be sourced using: 'source $0' ." 1>&2 ; exit 1 ; }


export PATH=$(dirname "${BASH_SOURCE}")/tools:${PATH}

cat << EOF

  Punchplatform starter kit provides these tools:

     simulate.sh   To generate a test flow of documents/logs (tcp/udp/kafka/lumberjack), or simulate a receiver for such

EOF