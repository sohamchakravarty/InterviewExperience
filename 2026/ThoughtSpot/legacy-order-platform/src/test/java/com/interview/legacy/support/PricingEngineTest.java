package com.interview.legacy.support;

import static org.junit.Assert.assertEquals;

import com.interview.legacy.domain.Customer;
import com.interview.legacy.domain.OrderItem;
import com.interview.legacy.domain.OrderRequest;
import java.util.Arrays;
import org.junit.Test;

public class PricingEngineTest {

    @Test
    public void testFreeshipCouponEliminatesStandardShipping() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 1, 50.0);
        OrderRequest request = new OrderRequest(
            "cust-1",
            "ext-1",
            "USD",
            false,
            "FREESHIP",
            Arrays.asList(item1),
            ""
        );
        Customer customer = new Customer("cust-1", "Test Customer", "test@example.com", "US", false, 5);

        // Act
        double total = PricingEngine.calculateTotal(request, customer);

        // Assert
        // Subtotal: 50.0
        // VIP discount: not applied (VIP=false)
        // Shipping: 0 (FREESHIP applied)
        // Tax: 50.0 * 0.0825 = 4.125
        // Expected total: 50.0 + 0 + 4.125 = 54.125
        assertEquals(54.125, total, 0.01);
    }

    @Test
    public void testFreeshipCouponEliminatesExpeditedShipping() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 2, 30.0);
        OrderRequest request = new OrderRequest(
            "cust-2",
            "ext-2",
            "USD",
            true,
            "FREESHIP",
            Arrays.asList(item1),
            ""
        );
        Customer customer = new Customer("cust-2", "Test Customer", "test@example.com", "US", false, 5);

        // Act
        double total = PricingEngine.calculateTotal(request, customer);

        // Assert
        // Subtotal: 30.0 * 2 = 60.0
        // VIP discount: not applied (VIP=false)
        // Shipping: 0 (FREESHIP applied, even though expedite=true)
        // Tax: 60.0 * 0.0825 = 4.95
        // Expected total: 60.0 + 0 + 4.95 = 64.95
        assertEquals(64.95, total, 0.01);
    }

    @Test
    public void testFreeshipWithVipCustomer() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 1, 100.0);
        OrderRequest request = new OrderRequest(
            "cust-1",
            "ext-3",
            "USD",
            false,
            "FREESHIP",
            Arrays.asList(item1),
            ""
        );
        Customer customer = new Customer("cust-1", "VIP Customer", "vip@example.com", "US", true, 5);

        // Act
        double total = PricingEngine.calculateTotal(request, customer);

        // Assert
        // Subtotal: 100.0
        // VIP discount: 100.0 * 0.93 = 93.0
        // Shipping: 0 (FREESHIP applied)
        // Tax: 93.0 * 0.0825 = 7.6725
        // Expected total: 93.0 + 0 + 7.6725 = 100.6725
        assertEquals(100.6725, total, 0.01);
    }

    @Test
    public void testFreeshipCouponComparedToStandardShipping() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 1, 100.0);
        OrderRequest requestWithFreeShip = new OrderRequest(
            "cust-1",
            "ext-4",
            "USD",
            false,
            "FREESHIP",
            Arrays.asList(item1),
            ""
        );
        OrderRequest requestWithoutCoupon = new OrderRequest(
            "cust-1",
            "ext-5",
            "USD",
            false,
            null,
            Arrays.asList(item1),
            ""
        );
        Customer customer = new Customer("cust-1", "Test Customer", "test@example.com", "US", false, 5);

        // Act
        double totalWithFreeship = PricingEngine.calculateTotal(requestWithFreeShip, customer);
        double totalWithoutCoupon = PricingEngine.calculateTotal(requestWithoutCoupon, customer);

        // Assert
        // Without coupon: 100.0 + 7.99 (standard shipping) + (100.0 * 0.0825) = 116.24
        // With FREESHIP: 100.0 + 0 (no shipping) + (100.0 * 0.0825) = 108.25
        // Difference should be 7.99 (the standard shipping cost)
        assertEquals(7.99, totalWithoutCoupon - totalWithFreeship, 0.01);
    }

    @Test
    public void testFreeshipWithEuropeanTaxRate() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 1, 100.0);
        OrderRequest request = new OrderRequest(
            "cust-3",
            "ext-6",
            "EUR",
            false,
            "FREESHIP",
            Arrays.asList(item1),
            ""
        );
        Customer customer = new Customer("cust-3", "EU Customer", "customer@eu.com", "EU", false, 10);

        // Act
        double total = PricingEngine.calculateTotal(request, customer);

        // Assert
        // Subtotal: 100.0
        // VIP discount: not applied (VIP=false)
        // Shipping: 0 (FREESHIP applied)
        // Tax: 100.0 * 0.20 = 20.0 (EU rate)
        // Expected total: 100.0 + 0 + 20.0 = 120.0
        assertEquals(120.0, total, 0.01);
    }

    @Test
    public void testFreeshipWithMultipleItems() {
        // Arrange
        OrderItem item1 = new OrderItem("SKU001", 2, 25.0);
        OrderItem item2 = new OrderItem("SKU002", 1, 50.0);
        OrderRequest request = new OrderRequest(
            "cust-1",
            "ext-7",
            "USD",
            false,
            "FREESHIP",
            Arrays.asList(item1, item2),
            ""
        );
        Customer customer = new Customer("cust-1", "Test Customer", "test@example.com", "US", false, 5);

        // Act
        double total = PricingEngine.calculateTotal(request, customer);

        // Assert
        // Subtotal: (2 * 25.0) + (1 * 50.0) = 50.0 + 50.0 = 100.0
        // VIP discount: not applied (VIP=false)
        // Shipping: 0 (FREESHIP applied)
        // Tax: 100.0 * 0.0825 = 8.25
        // Expected total: 100.0 + 0 + 8.25 = 108.25
        assertEquals(108.25, total, 0.01);
    }
}
