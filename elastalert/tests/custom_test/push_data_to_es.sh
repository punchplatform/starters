#!/bin/bash
BLUE='\033[0;34m'
GREEN='\033[0;32m'

INDICE="customers"
DATA=$1
for f in $DATA*
do
    echo -e "${BLUE}Uploading $f data in $INDICE ..."
    curl --silent -u elastic:elastic -H "Content-Type: application/json" -XPOST 172.19.0.2:9200/$INDICE/_doc -d @$f > /dev/null
done
echo -e "${GREEN}Data upload complete ...\n"
