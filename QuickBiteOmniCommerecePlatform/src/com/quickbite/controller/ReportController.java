package com.quickbite.controller;


import com.quickbite.entity.Product;
import com.quickbite.service.ReportService;
import java.util.Map;

public class ReportController {
 private ReportService reportService;

 public ReportController(ReportService reportService) { this.reportService = reportService; }	

 public void printAnalyticsDashboard() {
     System.out.println("\n====================================");
     System.out.println("         QUICKBITE EXECUTIVE ANALYTICS DASHBOARD       ");
     System.out.println("=======================================================");
     System.out.printf("Total Platform Gross Revenue : ₹%.2f\n", reportService.calculateTotalRevenue());

     System.out.println("\nCategory Wise Order Items Count:");
     Map<String, Long> categoryReport = reportService.getCategoryCountReport();
     categoryReport.forEach((cat, count) -> System.out.printf("  -> %-15s : %d item(s)\n", cat, count));

     Product topProduct = reportService.getTopSellingProduct();
     System.out.println("\nTop Selling Product SKU (MaxHeap Algorithm):");
     if (topProduct != null) {
         System.out.println("  [TOP SKU] " + topProduct.getName() + " (" + topProduct.getCategory().getCategoryName() + ")");
     } else {
         System.out.println("  No sales recorded.");
     }
     System.out.println("=======================================================\n");
 }
}
