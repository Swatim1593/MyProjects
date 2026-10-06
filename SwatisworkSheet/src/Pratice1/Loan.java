package Pratice1;

import java.time.LocalDate;

public class Loan  {
	private Book book;
	private Member member;
	private LocalDate dueDate;
	private LocalDate issueDate;
	private LocalDate returnDate;
	private boolean isReturned;
	
	// constructor — runs when you create a new Book object
	
	public Loan(Book book, Member member,LocalDate issueDate,LocalDate dueDate) {
		this.book=book;
		this.member=member;
		this.issueDate=issueDate;
		this.dueDate=dueDate;
		this.isReturned= false;
		this.returnDate= null;
		
		book.setAvailable(false);
		
	}
	 // getters — let other classes READ the private fields
	public Book getBook() {
		return book;
	}
	public Member getMember() {
		return member;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public boolean isReturned() {
		return isReturned;
	}
	
	public void returnBook() {
		this.returnDate=LocalDate.now();
		this.isReturned=true;
		book.setAvailable(true);
	}
	@Override
	public String toString() {
		return book.getTitle()+" borrowed by "+member.getName()+" on "+issueDate+"(Due: "+dueDate+")"+(isReturned?
				" -Returned on "+returnDate:" -Not returned");
	}

}
