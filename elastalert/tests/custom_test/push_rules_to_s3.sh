#!/bin/bash
BLUE='\033[0;34m'
GREEN='\033[0;32m'

bucket=default
files_location=$1
contentType="application/yaml"
dateValue=`date -R`
s3Key=minio
s3Secret=password

function pushToS3()
{
    files_path=$1
    for file_name in $files_path*
    do
        file=$(realpath $file_name)
        file_basename=$(basename $file)
        resource="/${bucket}/rules/${file_basename}"
        stringToSign="PUT\n\n${contentType}\n${dateValue}\n${resource}"
        signature=`echo -en ${stringToSign} | openssl sha1 -hmac ${s3Secret} -binary | base64`
        curl -X PUT -T "${file}" \
          -H "Date: ${dateValue}" \
          -H "Content-Type: ${contentType}" \
          -H "Authorization: AWS ${s3Key}:${signature}" \
          http://172.19.0.2:9002/${bucket}/rules/${file_basename}
        echo -e "${BLUE}Uploading rule ${file_basename} in http://172.19.0.2:9002/${bucket}/rules/"

    done
    echo -e "${GREEN}Rules upload complete ...\n"
}
pushToS3 $files_location