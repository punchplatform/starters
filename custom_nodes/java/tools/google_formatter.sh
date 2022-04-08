#!/usr/bin/env bash

set -o errexit
set -o pipefail

: ${JAVA_HOME:="$(which java)"}
: ${FORMATTER_JAR_PATH:="build/google_formatter/googleformatter.jar"}

${JAVA_HOME}/bin/java --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED \
    --add-exports jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED \
    --add-exports jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED \
    --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED \
    --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED \
    -jar ${FORMATTER_JAR_PATH} $@
