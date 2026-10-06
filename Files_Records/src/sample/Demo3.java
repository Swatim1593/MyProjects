package sample;

public class Demo3 {

    public static void main(String[] args) {

        try {

           String s = null;

           // System.out.println(s.length());
 System.out.println(10/0);
        }   //      catch (ArithmeticException e) {

            //System.out.println("Arithmetic Error");

      //  }
    catch (NullPointerException e) {

            System.out.println("Null Error");
        }
        catch (Exception e) {

            System.out.println(" Error");

        
        } 

    }
}