package Pratice1;

public class child {
	void add() {
		
		System.out.println("i am chil");
	}

	/*static class papa extends child{
		void edit() {
			this.add();
			System.out.println("i am pa");
		}
	
	void delete() {
		this.edit();
		System.out.println("i am ----");
	}
	}
	public static void main(String[] args) {
		
		papa c1= new papa();
		c1.delete();
		
	}

}*/




public class Month {
	void days() {
		System.out.println("there is 30 days");
	}

}
	
 	class August extends Month{
		void time() {
			this.days();
			System.out.println("ther is 31 days");
		}
 	}
	
public class main{
	public static void main(String[] args) {
		August a1=new August();
		a1.time();
		
		

	}
}
}