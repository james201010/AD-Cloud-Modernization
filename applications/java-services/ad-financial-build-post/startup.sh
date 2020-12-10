#!/usr/bin/env bash

if [ -z "$GRAPH_FILE" ]; then
    GRAPH_FILE=/app-graph.json
fi

JAVA_OPTS="$JAVA_OPTS -Xms64m -Xmx512m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true"
JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="$JAVA_OPTS -Dlog4j.configuration=/log4j.properties"

java $JAVA_OPTS -jar /AD-Java-Services-1.0.0.jar --app.graph.path=$GRAPH_FILE