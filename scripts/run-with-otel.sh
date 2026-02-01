#!/bin/bash
# 使用 OpenTelemetry Java Agent 啟動應用程式

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
AGENT_PATH="$PROJECT_DIR/docker/otel/opentelemetry-javaagent.jar"

# 檢查 agent 是否存在
if [ ! -f "$AGENT_PATH" ]; then
    echo "Agent not found. Downloading..."
    cd "$PROJECT_DIR/docker/otel" && bash download-agent.sh
fi

# 執行應用程式
cd "$PROJECT_DIR"
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="\
-javaagent:$AGENT_PATH \
-Dotel.service.name=message-demo \
-Dotel.exporter.otlp.endpoint=http://localhost:55681 \
-Dotel.exporter.otlp.protocol=http/protobuf \
-Dotel.traces.exporter=otlp \
-Dotel.metrics.exporter=none \
-Dotel.logs.exporter=none \
-Dotel.instrumentation.common.db-statement-sanitizer.enabled=false"
