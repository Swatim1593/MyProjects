package com.hospital.decorator;


public class LabTestDecorator extends ConsultationDecorator {
    private String testName;
    private double testCost;
    
    
    /*hospitalFacade.processFullConsultationAndBilling(patient1.getId(),cardioDoc,"ECG&Lipid Profile",1500.00,450.00,new UpiPayment("jyothi@okaxis")); 
        System.out.println();
*/

    public LabTestDecorator(BillableService decoratedService, String testName, double testCost) {
        super(decoratedService);
        this.testName = testName;
        this.testCost = testCost;
    }

    @Override
    public double getCost() {
        return decoratedService.getCost() + testCost;
    }

    @Override
    public String getDescription() {
        return decoratedService.getDescription() + " + Lab Test (" + testName + ")";
    }
}