package Pratice1;

public class Second {
static String school="ABC";
String name="Swati";
static void displaySchool() {  //Static
	System.out.println(school);
}
void displayName() {  //nonstatic
	System.out.println(name);
}

  
	public static void main(String[] args) {
		Second s1=new Second();  //creating an obj
		s1.displaySchool();
		s1.displayName();

	}

}
