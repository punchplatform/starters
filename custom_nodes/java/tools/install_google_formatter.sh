#!/usr/bin/env bash

set -o errexit
set -o pipefail

FORMATTER_RELEASE_URL=https://github.com/google/google-java-format/releases/download/v1.10.0/google-java-format-1.10.0-all-deps.jar
FORMATTER_JAR_PATH=$1

FORMATTER_DESTINATION=$(dirname $FORMATTER_JAR_PATH)

rm -rf $FORMATTER_DESTINATION
mkdir -p $FORMATTER_DESTINATION

curl -LJo $FORMATTER_JAR_PATH $FORMATTER_RELEASE_URL

echo -e "${CYAN}Script path:${NC} $0"
echo -e "${CYAN}Google Formatter Version:${NC} $FORMATTER_RELEASE_URL"
echo -e "${CYAN}Installed in:${NC} $FORMATTER_DESTINATION"

exit $?
