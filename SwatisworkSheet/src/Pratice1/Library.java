package Pratice1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
	private List<Book> books;
	private List<Member> members;
	private List<Loan> loans;
	
	public Library() {
		this.books=new ArrayList<Book>();
		this.members=new ArrayList<Member>();
		this.loans= new ArrayList<Loan>();
	}
	public void addBook(Book book) {
		books.add(book);
	}
 public void addMember (Member member) {
	 members.add(member);
 }
	 
	 public Book findBookByIsbn(String isbn) {
		 for(Book b:books) {
			 if(b.getIsbn().equals(isbn)) {
				 return b;
			 }
		 }
		 return null;
 }
	 public Member findMemberByRegId(String regId) {
		 for(Member m:members) {
			 if(m.getRegId().equals(regId)) {
				 return m;
			 }
		 }
		 return null;
	 }
	 public void issueBook (String isbn, String regId){
			Book book= findBookByIsbn(isbn);
			Member member= findMemberByRegId(regId);
			if(book==null  || member ==null) {
				System.out.println("Book or member not found");
				return;
			}
			
			
			LocalDate issueDate=LocalDate.now();
			LocalDate dueDate= issueDate.plusDays(14);
			Loan loan = new Loan (book,member,issueDate,dueDate);
			loans.add(loan);
			System.out.println("Book is issue: "+loan);
			
			
			
			
			}
	 public Book returnBookByIsbn(String isbn) {
			for(Loan l:loans) {
				if(l.getBook().getIsbn().equals(isbn) != l.isReturned());
				l.isReturned();
				System.out.println("No active loan found for than ISBN");
			}
			
			


		}

}

