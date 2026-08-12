package com.interview.legacy.app;

import com.interview.legacy.api.OrderHttpHandler;
import com.interview.legacy.integration.FraudCheckClient;
import com.interview.legacy.integration.InventoryClient;
import com.interview.legacy.integration.NotificationClient;
import com.interview.legacy.integration.PaymentGatewayClient;
import com.interview.legacy.persistence.CustomerRepository;
import com.interview.legacy.persistence.OrderEventStore;
import com.interview.legacy.persistence.OrderRepository;
import com.interview.legacy.persistence.RequestRepository;
import com.interview.legacy.support.AuditTrail;
import com.interview.legacy.support.ExpiringOrderCache;
import com.interview.legacy.support.MetricsCollector;
import com.interview.legacy.support.NaiveJsonParser;
import com.interview.legacy.support.OrderService;
import com.interview.legacy.support.ReconciliationScheduler;
import com.interview.legacy.support.ReportService;
import com.sun.net.httpserver.HttpServer;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;

public class LegacyOrderApplication {
    public static void main(String[] args) throws Exception {
        Properties properties = loadProperties();
        int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        String reportTitle = properties.getProperty("report.title", "legacy-order-report");

        OrderRepository orderRepository = new OrderRepository();
        CustomerRepository customerRepository = new CustomerRepository();
        FraudCheckClient fraudCheckClient = new FraudCheckClient();
        InventoryClient inventoryClient = new InventoryClient();
        PaymentGatewayClient paymentGatewayClient = new PaymentGatewayClient();
        NotificationClient notificationClient = new NotificationClient();
        ExpiringOrderCache expiringOrderCache = new ExpiringOrderCache();
        AuditTrail auditTrail = new AuditTrail();
        OrderEventStore orderEventStore = new OrderEventStore();
        MetricsCollector metricsCollector = new MetricsCollector();
        ReconciliationScheduler reconciliationScheduler = new ReconciliationScheduler();
        RequestRepository requestRepository = new RequestRepository();
        OrderService orderService = new OrderService(
            requestRepository,
            orderRepository,
            customerRepository,
            fraudCheckClient,
            inventoryClient,
            paymentGatewayClient,
            notificationClient,
            expiringOrderCache,
            auditTrail,
            orderEventStore
        );
        ReportService reportService = new ReportService(orderRepository, paymentGatewayClient, auditTrail, reportTitle);
        OrderHttpHandler handler = new OrderHttpHandler(
            new NaiveJsonParser(),
            orderService,
            reportService,
            metricsCollector,
            auditTrail,
            reconciliationScheduler
        );

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", handler);
        server.setExecutor(Executors.newFixedThreadPool(4));
        reconciliationScheduler.start(orderRepository, auditTrail);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
        System.out.println("Legacy Order Platform started on port " + port);
    }

    private static Properties loadProperties() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream =
            LegacyOrderApplication.class.getClassLoader().getResourceAsStream("application.properties");
        if (inputStream != null) {
            properties.load(inputStream);
        }
        return properties;
    }
}
