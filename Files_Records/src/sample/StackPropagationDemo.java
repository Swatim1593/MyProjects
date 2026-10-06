package sample;

public class StackPropagationDemo {
	
	void level3() {
		int fault=10/0;  //ArithmaticException throw here
	}
	void level2() {
		level3();   //Delegates call
	}
	
	void level1() {
		try {
			level2();   //Exception propagates up trrough level2 into level1
		}catch(ArithmeticException e) {
			System.out.println("Caught propagated exception inside level1(): "+e.toString());
		}
	}

	public static void main(String[] args) {
		new StackPropagationDemo().level1();
		System.out.println("Program resumed execution safely");

	}

}
