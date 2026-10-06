package sample;

public class ExceptionFlowDemo {

    public static void main(String[] args) {
        System.out.println("--- Starting Program Execution ---");

        int dividend = 100;
        int divisor = 0;

        try {
            System.out.println("Entering try block...");
            int result = dividend / divisor; // Triggers ArithmeticException
            System.out.println("This line will NEVER execute due to exception above: " + result);
        } catch (ArithmeticException e) {
            System.err.println("Caught Exception: Cannot divide by zero! Message: " + e.getMessage());
        } finally {
            System.out.println("FINALLY BLOCK: Runs unconditionally (ideal for resource cleanup).");
        }

        System.out.println("--- Program continued normally past catch block ---");
    }
}