package excepCatch;

public class MultipleCach {

	public static void main(String[] args) 
	{
		try
		{
		String s= null;
		System.out.println(s.length());
		}
		catch(ArithmeticException e)
		{
			System.out.println("Arithmetic Exception");
		}
		catch(NullPointerException e)
		{
			System.out.println("NullPointer Exception");
		}

	}

}
