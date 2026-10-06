package Pra2;

public class Example1 {
	public static void main(String[]args) {
	String a= "java";
	String rev="";
	for(int i=a.length()-1;i>=0;i--) {
		
		rev+= a.charAt(i);
		System.out.println(rev);
	}
    System.out.println("Rev="+rev);
    
    
}
}
