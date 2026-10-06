package com.dispatch.queue;

import com.dispatch.event.RideRequestEvent;
import java.util.concurrent.BlockingQueue;

public class RideRequestProducer {
    private final BlockingQueue<RideRequestEvent> sharedQueue;

    public RideRequestProducer(BlockingQueue<RideRequestEvent> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    public boolean publishRequest(RideRequestEvent event) {
        return sharedQueue.offer(event);
    }
}