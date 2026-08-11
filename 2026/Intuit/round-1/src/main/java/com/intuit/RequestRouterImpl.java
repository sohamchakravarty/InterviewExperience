package com.intuit;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import com.intuit.model.Request;

public class RequestRouterImpl implements RequestRouter {

    private PriorityBlockingQueue<Request> requestQueue = 
        new PriorityBlockingQueue<>(10, new RequestComparator());

    @Override
    public synchronized void enqueue(Request request) {
        requestQueue.offer(request);
    }

    @Override
    public Request getNextRequest() {
        return requestQueue.poll();
    }

    class RequestComparator implements Comparator<Request> {

        @Override
        public int compare(Request r1, Request r2) {
            int result = 
                r1.getTenant().getTenantType().getPriority() - r2.getTenant().getTenantType().getPriority();

            if (result == 0) {
                return r1.getCreateTime().compareTo(r2.getCreateTime());
            }

            return result;
        }
    }

}
