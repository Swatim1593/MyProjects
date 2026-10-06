package excepCatch;

public class RealtimeLogin {
	static void validateAge(int age)
	{
		if (age<18)
		{
			throw new ArithmeticException("not eligible to vote");
			
		}
		else
		{
			System.out.println("Eligble to vote");
		
		
	     }
	}

	public static void main(String[] args) 
	{
		validateAge(15);
	}

}


