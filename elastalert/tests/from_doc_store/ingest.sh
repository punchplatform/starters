#!/bin/bash

DATA=$1/data
RULES=$1/rules
while true
do
    for f in $DATA/*
    do
    echo "Uploading $f data..."
    curl --silent -u elastic:elastic -H "Content-Type: application/json" -XPOST '172.19.0.2:9200/customers/_doc' -d @$f > /dev/null
    done

    for r in $RULES/*
    do
    echo "Uploading $r rules..."
    curl --silent -u elastic:elastic -H "Content-Type: application/json" -XPOST '172.19.0.2:9200/rules/_doc' -d @$r > /dev/null
    done

    break
done