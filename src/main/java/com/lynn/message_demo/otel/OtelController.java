package com.lynn.message_demo.otel;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

/**
 * @Author: Lynn on 2025/1/2
 */
public class OtelController {
  private static final Tracer tracer = GlobalOpenTelemetry.getTracer("example-service");

  public void exampleMethod() {
    Span span = tracer.spanBuilder("example-span")
        .startSpan();
    try {
      // 執行業務邏輯
    } finally {
      span.end();
    }
  }

//  public static void main(String[] args) {
//    // 設置 OTLP Exporter
//    OtlpGrpcSpanExporter spanExporter = OtlpGrpcSpanExporter.builder()
//        .setEndpoint("http://localhost:55681") // 根據需求修改為 HTTP 或 gRPC
//        .build();
//
//    // 配置 Tracer
//    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
//        .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build())
//        .build();
//
//    OpenTelemetrySdk openTelemetry = OpenTelemetrySdk.builder()
//        .setTracerProvider(tracerProvider)
//        .buildAndRegisterGlobal();
//
//    Tracer tracer = GlobalOpenTelemetry.getTracer("TestTracer");
//
//    // 生成一個簡單的 Span
//    Span span = tracer.spanBuilder("TestSpan")
//        .startSpan();
//
//    span.addEvent("TestEvent");
//    span.end();
//
//    System.out.println("Trace generated and exported.");
//  }


}
