package sample;

@FunctionalInterface
interface SalaryCalculator {
    // Single Abstract Method (SAM)
    double calculate(double baseSalary, double allowance);

    // Default method (does not break SAM rule)
    default void printNotice() {
        System.out.println("Notice: Salary figures are subject to standard TDS tax deductions.");
    }

    // Static helper method (does not break SAM rule)
    static double calculatePFDeduction(double baseSalary) {
        return baseSalary * 0.12; // 12% PF contribution
    }
}

public class CustomFIDemo {
    public static void main(String[] args) {
        // Lambda implementation 1: Standard Pay Calculation
        SalaryCalculator standardPay = (base, allowance) -> base + allowance;

        // Lambda implementation 2: Appraisal Pay Calculation (Includes 15% Bonus)
        SalaryCalculator appraisalPay = (base, allowance) -> (base * 1.15) + allowance;

        double karthikPay = standardPay.calculate(60000, 10000);
        double ananyaPay = appraisalPay.calculate(45000, 8000);

        System.out.println("Standard Monthly Pay for Karthik : ₹" + karthikPay);
        System.out.println("Appraisal Monthly Pay for Ananya : ₹" + ananyaPay);
        System.out.println("PF Deduction for Suresh (Base 75k): ₹" + SalaryCalculator.calculatePFDeduction(75000));

        // Calling default method
        standardPay.printNotice();
    }
}