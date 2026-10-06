package sample;
//Custom Checked Domain Exception
class InsufficientBalanceException extends Exception {
 public InsufficientBalanceException(String message) {
     super(message);
 }
}

class BankAccount {
 private double balance = 1000.00;

 public void withdraw(double amount) throws InsufficientBalanceException {
     if (amount > balance) {
         throw new InsufficientBalanceException("Withdrawal of $" + amount + " exceeds balance of $" + balance);
     }
     balance -= amount;
     System.out.println("Withdrawal approved. Remaining balance: $" + balance);
 }
}

public class CustomExceptionDemo {
 public static void main(String[] args) {
     BankAccount account = new BankAccount();

     try {
         account.withdraw(1500.00); // Throws domain exception
     } catch (InsufficientBalanceException e) {
         System.err.println("Banking Error: " + e.getMessage());
     }
 }
}