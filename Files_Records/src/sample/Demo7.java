package sample;

public class Demo7 {

    public static void main(String[] args) {

        int age = 15;

        if(age < 18) {

            throw new ArithmeticException(
                    "Not Eligible For Voting");
        }
    }}