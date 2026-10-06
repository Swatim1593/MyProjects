package excepCatch;

public class Simplecach {

	public static void main(String[] args) 
	{
		try {
			int a=10/0;
			System.out.println(a);
		}
		catch (ArithmeticException e) 
		{
			System.out.println("can not drive by Zereo");
			
		}
		System.out.println("program continues.......");

	}

}
