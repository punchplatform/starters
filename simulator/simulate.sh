#!/bin/bash -ue

: ${IMG:="ghcr.io/punchplatform/simulator:8.0.0"}
: ${FILE:="simulator.json"}


function fatal () { echo "FATAL ERROR: $*" 1>&2 ; exit 1 ; }

if [ "${1:-}" == "--help" ] ; then

    # --help is supported by the simulator tool itself, so pass it through

    COMMAND=(docker run --rm "${IMG}" --help)

else
    
    # Check that local configuration file/folder exists before calling docker

    [ -e "${FILE}" ] || { "$0" --help ; fatal "No such local simulator configuration path: '${FILE}'" ; }

    COMMAND=(docker run \
    --rm \
    --network=host \
    -v "$(realpath ${FILE}):/punch/${FILE}" "${IMG}" \
    -c "${FILE}" "$@" )

fi

echo "${COMMAND[@]}"

eval "${COMMAND[@]}"
exit $?