package collectionExamp;

public class NoDataFoundExcept extends RuntimeException 
{

	private String message;
	public NoDataFoundExcept(String message) {
		super();
		this.message=message;
		System.out.println(message);

	}

}
