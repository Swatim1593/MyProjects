package Pratice1;

 class realoverload {
	 int id;
	 String name;
	 boolean Salary;
	 
	 realoverload(int id ,String name){
		 this.id=id;
		 this.name=name;
		 this.Salary=Salary;
	 }
		 realoverload(int id,String name, boolean Salary){
			
			 //this.Salary;
		
		 
 }
	 void display() {
		 System.out.println("ID:"+id);
		 System.out.println("NAME:"+name);
		 System.out.println("Salary:"+Salary);
	 }
	public static void main(String[] args) {
		realoverload r1=new realoverload(10, "Swati");
		r1.display();

	}

}
