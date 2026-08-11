package com.razorpay.strategy;

import com.razorpay.models.IPAddress;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConsistentHashingStrategyTest {

    @Test
    void returnsSameAddressForSameKey() {
        IPAddress ip1 = new IPAddress("ip1", true);
        IPAddress ip2 = new IPAddress("ip2", true);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(ip1, ip2));

        strategy.setRequestKey("user-123");
        IPAddress first = strategy.getIpAddress();

        strategy.setRequestKey("user-123");
        IPAddress second = strategy.getIpAddress();

        assertEquals(first, second);
    }

    @Test
    void differentKeysMayRouteToSameOrDifferentAddresses() {
        IPAddress ip1 = new IPAddress("ip1", true);
        IPAddress ip2 = new IPAddress("ip2", true);
        Set<IPAddress> validNodes = Set.of(ip1, ip2);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(ip1, ip2));

        for (int i = 0; i < 50; i++) {
            strategy.setRequestKey("key-" + i);
            assertTrue(validNodes.contains(strategy.getIpAddress()));
        }
    }

    @Test
    void returnsOnlyHealthyAddresses() {
        IPAddress healthy = new IPAddress("ip1", true);
        IPAddress unhealthy = new IPAddress("ip2", false);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(healthy, unhealthy));

        for (int i = 0; i < 20; i++) {
            strategy.setRequestKey("key-" + i);
            assertEquals(healthy, strategy.getIpAddress());
        }
    }

    @Test
    void throwsWhenAllAddressesUnhealthy() {
        IPAddress ip1 = new IPAddress("ip1", false);
        IPAddress ip2 = new IPAddress("ip2", false);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(ip1, ip2));

        strategy.setRequestKey("any-key");
        assertThrows(IllegalStateException.class, strategy::getIpAddress);
    }

    @Test
    void throwsWhenListIsEmpty() {
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of());

        strategy.setRequestKey("any-key");
        assertThrows(IllegalStateException.class, strategy::getIpAddress);
    }

    @Test
    void handlesEmptyRequestKey() {
        IPAddress ip1 = new IPAddress("ip1", true);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(ip1));

        strategy.setRequestKey("");
        assertNotNull(strategy.getIpAddress());
    }

    @Test
    void singleNodeAlwaysReturnsItself() {
        IPAddress ip = new IPAddress("ip1", true);
        ConsistentHashingStrategy strategy = new ConsistentHashingStrategy(List.of(ip));

        for (int i = 0; i < 10; i++) {
            strategy.setRequestKey("key-" + i);
            assertEquals(ip, strategy.getIpAddress());
        }
    }
}