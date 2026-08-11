package com.razorpay.strategy;

import com.razorpay.models.IPAddress;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundRobinStrategyTest {

    @Test
    void returnsSingleHealthyAddress() {
        IPAddress ip = new IPAddress("ip1", true);
        RoundRobinStrategy strategy = new RoundRobinStrategy(List.of(ip));

        assertEquals(ip, strategy.getIpAddress());
    }

    @Test
    void cyclesThroughHealthyAddressesInOrder() {
        IPAddress ip1 = new IPAddress("ip1", true);
        IPAddress ip2 = new IPAddress("ip2", true);
        IPAddress ip3 = new IPAddress("ip3", true);
        RoundRobinStrategy strategy = new RoundRobinStrategy(List.of(ip1, ip2, ip3));

        assertEquals(ip1, strategy.getIpAddress());
        assertEquals(ip2, strategy.getIpAddress());
        assertEquals(ip3, strategy.getIpAddress());
        assertEquals(ip1, strategy.getIpAddress());
    }

    @Test
    void skipsUnhealthyAddresses() {
        IPAddress ip1 = new IPAddress("ip1", true);
        IPAddress ip2 = new IPAddress("ip2", false);
        IPAddress ip3 = new IPAddress("ip3", true);
        RoundRobinStrategy strategy = new RoundRobinStrategy(List.of(ip1, ip2, ip3));

        assertEquals(ip1, strategy.getIpAddress());
        assertEquals(ip3, strategy.getIpAddress());
        assertEquals(ip1, strategy.getIpAddress());
    }

    @Test
    void throwsWhenAllAddressesUnhealthy() {
        IPAddress ip1 = new IPAddress("ip1", false);
        IPAddress ip2 = new IPAddress("ip2", false);
        RoundRobinStrategy strategy = new RoundRobinStrategy(List.of(ip1, ip2));

        assertThrows(IllegalStateException.class, strategy::getIpAddress);
    }

    @Test
    void throwsWhenListIsEmpty() {
        RoundRobinStrategy strategy = new RoundRobinStrategy(List.of());

        assertThrows(IllegalStateException.class, strategy::getIpAddress);
    }
}