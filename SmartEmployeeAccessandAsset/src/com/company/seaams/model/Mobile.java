package com.company.seaams.model;

public class Mobile extends Asset {
    public Mobile(String assetTag, String assetName, double assetCost) {
        super(assetTag, assetName, assetCost);
    }

    @Override
    public void displayAsset() {
        System.out.format("TYPE: MOBILE  | Tag: %-8s | Model: %-15s | Cost: $%-8.2f | Status: %s%n",
                assetTag, assetName, assetCost, (isAllocated ? "ALLOCATED TO ID " + assignedEmployeeId : "AVAILABLE"));
    }
}