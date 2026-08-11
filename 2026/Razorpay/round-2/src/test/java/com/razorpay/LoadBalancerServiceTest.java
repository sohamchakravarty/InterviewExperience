package com.razorpay;

import com.razorpay.models.IPAddress;
import com.razorpay.models.Service;
import com.razorpay.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerServiceTest {

    private LoadBalancerService lb;

    @BeforeEach
    void setUp() {
        lb = new LoadBalancerService();
    }

    @Test
    void routeToIPThrowsForUnknownEndpoint() {
        assertThrows(IllegalArgumentException.class, () -> lb.routeToIP("/unknown"));
    }

    @Test
    void routeToIPReturnsIPAfterServiceAdded() {
        IPAddress ip = new IPAddress("10.0.0.1", true);
        Service service = new Service("svc1", List.of(ip), List.of("/api"), new RoundRobinStrategy(List.of(ip)));

        lb.addService(service);

        assertEquals("10.0.0.1", lb.routeToIP("/api"));
    }

    @Test
    void addServiceRegistersAllEndpoints() {
        IPAddress ip = new IPAddress("10.0.0.1", true);
        Service service = new Service("svc1", List.of(ip), List.of("/a", "/b", "/c"), new RoundRobinStrategy(List.of(ip)));

        lb.addService(service);

        assertEquals("10.0.0.1", lb.routeToIP("/a"));
        assertEquals("10.0.0.1", lb.routeToIP("/b"));
        assertEquals("10.0.0.1", lb.routeToIP("/c"));
    }

    @Test
    void multipleServicesRouteToTheirOwnIPs() {
        IPAddress ip1 = new IPAddress("10.0.0.1", true);
        IPAddress ip2 = new IPAddress("10.0.0.2", true);
        Service svc1 = new Service("svc1", List.of(ip1), List.of("/users"), new RoundRobinStrategy(List.of(ip1)));
        Service svc2 = new Service("svc2", List.of(ip2), List.of("/orders"), new RoundRobinStrategy(List.of(ip2)));

        lb.addService(svc1);
        lb.addService(svc2);

        assertEquals("10.0.0.1", lb.routeToIP("/users"));
        assertEquals("10.0.0.2", lb.routeToIP("/orders"));
    }

    @Test
    void roundRobinStrategyRotatesAcrossCalls() {
        IPAddress ip1 = new IPAddress("10.0.0.1", true);
        IPAddress ip2 = new IPAddress("10.0.0.2", true);
        Service service = new Service("svc1", List.of(ip1, ip2), List.of("/api"), new RoundRobinStrategy(List.of(ip1, ip2)));

        lb.addService(service);

        String first = lb.routeToIP("/api");
        String second = lb.routeToIP("/api");
        String third = lb.routeToIP("/api");

        assertNotEquals(first, second);
        assertEquals(first, third);
    }

    @Test
    void laterServiceOverwritesSharedEndpoint() {
        IPAddress ip1 = new IPAddress("10.0.0.1", true);
        IPAddress ip2 = new IPAddress("10.0.0.2", true);
        Service svc1 = new Service("svc1", List.of(ip1), List.of("/shared"), new RoundRobinStrategy(List.of(ip1)));
        Service svc2 = new Service("svc2", List.of(ip2), List.of("/shared"), new RoundRobinStrategy(List.of(ip2)));

        lb.addService(svc1);
        lb.addService(svc2);

        assertEquals("10.0.0.2", lb.routeToIP("/shared"));
    }

    @Test
    void addServiceWithNoEndpointsDoesNotBreakRouting() {
        IPAddress ip = new IPAddress("10.0.0.1", true);
        Service service = new Service("svc1", List.of(ip), List.of(), new RoundRobinStrategy(List.of(ip)));

        lb.addService(service);

        assertThrows(IllegalArgumentException.class, () -> lb.routeToIP("/anything"));
    }

    @Test
    void throwsWhenServiceHasNoHealthyIPs() {
        IPAddress ip = new IPAddress("10.0.0.1", false);
        Service service = new Service("svc1", List.of(ip), List.of("/api"), new RoundRobinStrategy(List.of(ip)));

        lb.addService(service);

        assertThrows(IllegalStateException.class, () -> lb.routeToIP("/api"));
    }
}
