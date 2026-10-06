package com.company.seaams.model;

import java.util.LinkedList;
import java.util.Queue;

public class RequestService {
    private final Queue<ServiceRequest> executionQueue = new LinkedList<ServiceRequest>();

    public void raiseRequest(ServiceRequest request) {
        executionQueue.offer(request);
        System.out.println("[QUEUED] Request added to process pipeline: " + request);
    }

    public void processNextRequest() {
        ServiceRequest pendingReq = executionQueue.poll();
        if (pendingReq == null) {
            System.out.println("[IDLE] Queue pipeline currently empty. No pending requests.");
            return;
        }
        pendingReq.setStatus("CLOSED");
        System.out.println("[PROCESSED] Successfully updated state to CLOSED for: " + pendingReq);
    }

    public void listPendingRequests() {
        if (executionQueue.isEmpty()) {
            System.out.println("No active requests pending in the execution pipeline.");
            return;
        }
        for (ServiceRequest req : executionQueue) {
            System.out.println(req);
        }
    }
}