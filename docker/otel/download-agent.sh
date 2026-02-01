#!/bin/bash
# 下載 OpenTelemetry Java Agent

OTEL_VERSION="2.11.0"
AGENT_FILE="opentelemetry-javaagent.jar"

if [ -f "$AGENT_FILE" ]; then
    echo "Agent already exists: $AGENT_FILE"
else
    echo "Downloading OpenTelemetry Java Agent v${OTEL_VERSION}..."
    curl -L -o "$AGENT_FILE" \
        "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_VERSION}/opentelemetry-javaagent.jar"
    echo "Download complete: $AGENT_FILE"
fi
