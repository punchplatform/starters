#

# The purpose of this is to include some useful tools in your path
# juste source it:

# source ./activate.sh

export PATH=$(pwd)/tools:${PATH}

cat << EOF

  Punchplatform starter kit provides these tools:

     simulate.sh   To generate a test flow of documents/logs (tcp/udp/kafka/lumberjack), or simulate a receiver for such

EOF