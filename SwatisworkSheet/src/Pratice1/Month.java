package Pratice1;

public class Month {
	void day() {
		System.out.println("it is 30 days");
	}
}

class August extends Month {
	void time() {
		this.day();
		System.out.println("it is 31 days");
	}

	public static void main(String[] args) {
		August a1 = new August();
		a1.time();
	}



int[] num = {10,60,40};



		
}