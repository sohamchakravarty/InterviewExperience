package com.intuit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.intuit.model.Request;
import com.intuit.model.Tenant;
import com.intuit.model.TenantType;

public class RequestRouterTest {
    private RequestRouter requestRouter;

    @BeforeEach
    void setUp() {
        this.requestRouter = new RequestRouterImpl();
    }

    @Test
    void test1() {
        Request r1 = new Request(new Tenant(TenantType.ENTERPRISE));
        Request r2 = new Request(new Tenant(TenantType.FREE));
        Request r3 = new Request(new Tenant(TenantType.ENTERPRISE));
        Request r4 = new Request(new Tenant(TenantType.PREMIUM));
        Request r5 = new Request(new Tenant(TenantType.ENTERPRISE));

        this.requestRouter.enqueue(r1);
        this.requestRouter.enqueue(r2);
        this.requestRouter.enqueue(r3);
        this.requestRouter.enqueue(r4);
        this.requestRouter.enqueue(r5);

        assertEquals(r1, this.requestRouter.getNextRequest());
        assertEquals(r3, this.requestRouter.getNextRequest());
        assertEquals(r5, this.requestRouter.getNextRequest());
        assertEquals(r4, this.requestRouter.getNextRequest());
        assertEquals(r2, this.requestRouter.getNextRequest());
    }

    @Test
    void testConcurrentEnqueue() throws InterruptedException {
        int threadCount = 10;
        int requestsPerThread = 100;
        TenantType[] types = TenantType.values();

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            TenantType type = types[i % types.length];
            new Thread(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < requestsPerThread; j++) {
                        requestRouter.enqueue(new Request(new Tenant(type)));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        doneLatch.await();

        int count = 0;
        int lastPriority = -1;
        Request r;
        while ((r = requestRouter.getNextRequest()) != null) {
            count++;
            int priority = r.getTenant().getTenantType().getPriority();
            assertTrue(priority >= lastPriority, "Priority ordering violated");
            lastPriority = priority;
        }
        assertEquals(threadCount * requestsPerThread, count, "Some enqueued requests were lost");
    }
}
