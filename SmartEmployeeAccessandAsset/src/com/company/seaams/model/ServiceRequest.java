package com.company.seaams.model;

public class ServiceRequest {
    private int requestId;
    private int employeeId;
    private String requestType;
    private String status;

    public ServiceRequest(int requestId, int employeeId, String requestType, String status) {
        this.requestId = requestId;
        this.employeeId = employeeId;
        this.requestType = requestType;
        this.status = status;
    }

    public int getRequestId() 
    { 
    	return requestId; 
    }
    public int getEmployeeId() 
    {
    	return employeeId;
    	}
    public String getRequestType()
    {
    	return requestType;
    	}
    public String getStatus()
    {
    	return status; 
    	}

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ReqID: %-6d | EmpID: %-6d | Type: %-15s | Status: %s", 
                requestId, employeeId, requestType, status);
    }
}