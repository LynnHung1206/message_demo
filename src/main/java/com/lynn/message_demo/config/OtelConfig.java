package com.lynn.message_demo.config;

import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.contrib.sampler.RuleBasedRoutingSampler;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
import io.opentelemetry.semconv.UrlAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

/**
 * @Author: Lynn on 2025/1/2
 */
@Configuration
public class OtelConfig {

  @Bean
  public AutoConfigurationCustomizerProvider otelCustomizer2() {
    return p ->
        //自訂採樣器以從追蹤中排除運行狀況檢查端點
        p.addSamplerCustomizer(
                (fallback, config) ->
                    RuleBasedRoutingSampler.builder(SpanKind.SERVER, fallback)
                        .drop(UrlAttributes.URL_PATH, "^/actuator")
                        .build())
            // 設定 OTLP 匯出器。此配置會取代預設的 OTLP 匯出器並為請求新增自訂標頭。
            .addSpanExporterCustomizer(
                (exporter, config) -> {
                  if (exporter instanceof OtlpHttpSpanExporter) {
                    return ((OtlpHttpSpanExporter) exporter)
                        .toBuilder()
                        .setHeaders(this::headers).build();
                  }
                  return exporter;
                });
  }


  private Map<String, String> headers() {
    return Collections.singletonMap("Authorization", "Bearer " + refreshToken());
  }

  private String refreshToken() {
    // e.g. read the token from a kubernetes secret
    return "token";
  }


}
