package com.intuit;

import com.intuit.model.Request;

public interface RequestRouter {
    void enqueue(Request request);

    Request getNextRequest();
}
