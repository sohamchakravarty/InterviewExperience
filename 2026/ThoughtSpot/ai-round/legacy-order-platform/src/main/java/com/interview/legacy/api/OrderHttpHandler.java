package com.interview.legacy.api;

import com.interview.legacy.domain.Order;
import com.interview.legacy.domain.OrderRequest;
import com.interview.legacy.support.AuditTrail;
import com.interview.legacy.support.MetricsCollector;
import com.interview.legacy.support.NaiveJsonParser;
import com.interview.legacy.support.OrderService;
import com.interview.legacy.support.ReportService;
import com.interview.legacy.support.ResponseWriter;
import com.interview.legacy.support.ReconciliationScheduler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OrderHttpHandler implements HttpHandler {
    private final NaiveJsonParser parser;
    private final OrderService orderService;
    private final ReportService reportService;
    private final MetricsCollector metricsCollector;
    private final AuditTrail auditTrail;
    private final ReconciliationScheduler reconciliationScheduler;

    public OrderHttpHandler(
        NaiveJsonParser parser,
        OrderService orderService,
        ReportService reportService,
        MetricsCollector metricsCollector,
        AuditTrail auditTrail,
        ReconciliationScheduler reconciliationScheduler
    ) {
        this.parser = parser;
        this.orderService = orderService;
        this.reportService = reportService;
        this.metricsCollector = metricsCollector;
        this.auditTrail = auditTrail;
        this.reconciliationScheduler = reconciliationScheduler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        long start = System.currentTimeMillis();
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            if ("POST".equalsIgnoreCase(method) && "/orders".equals(path)) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                String requestId = exchange.getRequestHeaders().getFirst("X-Request-Id");
                OrderRequest request = parser.parseOrderRequest(body);
                Order order = orderService.placeOrder(requestId, request);
                ResponseWriter.writeJson(exchange, 201, order.toJson());
                return;
            }

            if ("GET".equalsIgnoreCase(method) && "/orders".equals(path)) {
                ResponseWriter.writeJson(exchange, 200, orderService.listOrdersAsJson());
                return;
            }

            if ("GET".equalsIgnoreCase(method) && "/admin/report".equals(path)) {
                String report = reportService.generateCsvReport();
                ResponseWriter.writeText(exchange, 200, "text/csv", report);
                return;
            }

            if ("GET".equalsIgnoreCase(method) && "/health".equals(path)) {
                String body =
                    "{\"status\":\"ok\",\"orders\":"
                        + orderService.orderCount()
                        + ",\"cacheEntries\":"
                        + orderService.cacheSize()
                        + ",\"auditEvents\":"
                        + auditTrail.size()
                        + ",\"reconciliationSnapshots\":"
                        + reconciliationScheduler.historySize()
                        + "}";
                ResponseWriter.writeJson(exchange, 200, body);
                return;
            }

            ResponseWriter.writeJson(exchange, 404, "{\"error\":\"not found\"}");
        } catch (Exception exception) {
            auditTrail.record("request-failed path=" + path + " message=" + exception.getMessage());
            ResponseWriter.writeJson(exchange, 500, "{\"error\":\"" + safe(exception.getMessage()) + "\"}");
        } finally {
            metricsCollector.record(method + " " + path, System.currentTimeMillis() - start);
        }
    }

    private String safe(String value) {
        if (value == null) {
            return "unknown";
        }
        return value.replace("\"", "'");
    }
}
