package Pratice1;

public class Member {
	private String name ;
	private String regId;
	private String mailId;
	
	// constructor — runs when you create a new Book object
	
	public Member(String name,String regId, String mailId) {
		this.mailId=mailId;
		this.name=name;
		this.regId=regId;
	}
	
	 // getters — let other classes READ the private fields
	public String getName(){
		return name;
	}
	public String getMailId() {
		return mailId;
	}
	public String getRegId() {
		return regId;
	}
	
	// setter — only for things that should change after creation
	public void setName(String name) {
		this.name=name;
	}
	
	  // toString — makes printing the object readable
	@Override
	public String toString() {
	 return name;
		
	}
	
}
