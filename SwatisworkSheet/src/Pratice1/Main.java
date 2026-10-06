package Pratice1;

import java.time.LocalDate;

public class Main {
	public static void main(String[]args) {
		Book book= new Book("musafir cafe","Divya Prakash Dubey","55-77869");
		Member member=new Member("Swati", "55","swati@gmail.com");
		
		 LocalDate issueDate = LocalDate.now();
	        LocalDate dueDate = issueDate.plusDays(14);
	        
	        Loan loan =new Loan(book, member,issueDate,dueDate);
			

	        // Print loan before returning
	        System.out.println("Before returning:");
	        System.out.println(loan);

	        // Return the book
	        loan.returnBook();

	        // Print loan after returning
	        System.out.println("\nAfter returning:");
	        System.out.println(loan);
	        
	  

	}
	

}
