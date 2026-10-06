package com.hello.Hello;

public class Reversnum {

	public static void main(String[] args) 
	{
		int num=12345;
		int rev =0;
		while(num!=0) {
			int dig=num%10;
			rev= rev*10+dig;
			num=num/10;
		}
		System.out.println(rev);

	}

}
