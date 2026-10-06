package sample;

public class Demo2 {

    public static void main(String[] args) {

        System.out.println("Start");

        try {

            int result = 10 / 2;

        } 
        
        //System.out.println();
        catch (ArithmeticException e) {

            System.out.println("Division by zero not allowed");
        }

        System.out.println("End");
    }
}