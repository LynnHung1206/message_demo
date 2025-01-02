package com.lynn.message_demo.otel;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: Lynn on 2025/1/2
 */
@Component
public class TracingInterceptor implements HandlerInterceptor {
  private static final Tracer tracer = GlobalOpenTelemetry.getTracer("example-service");

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Span span = tracer.spanBuilder(request.getRequestURI())
        .setAttribute("http.method", request.getMethod())
        .setAttribute("http.url", request.getRequestURI())
        .startSpan();

    request.setAttribute("span", span);

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    Span span = (Span) request.getAttribute("span");
    if (ex != null) {
      span.recordException(ex).setStatus(StatusCode.ERROR,"request method %s failed");
    }
    if (span != null) {
      span.end();
    }
  }

}
