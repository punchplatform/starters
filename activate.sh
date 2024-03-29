#!/bin/bash -ue
# The purpose of this script is to include in your terminal path
# some useful tools. Simply source it as follows:
# 
# source ./activate.sh

[ "${BASH_SOURCE[0]}" != "" ] || { echo "This file is supposed to be sourced using: 'source $0' ." 1>&2 ; exit 1 ; }

export PATH="$(dirname "$(realpath "${BASH_SOURCE[0]}")")"/simulator:${PATH}
cat << EOF

  Punchplatform starter kit provides these tools:

     simulate.sh   To generate a test flow of documents/logs (tcp/udp/kafka/lumberjack), or simulate a receiver for such

EOF