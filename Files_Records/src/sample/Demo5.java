package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Demo5 {
	static FileInputStream fis;
	public static void main(String[] args) {
		  

   try {
	    fis= new FileInputStream("emp.txt");
   }catch
   (FileNotFoundException e) {
	   System.out.println("File not Found");
   }
   finally {
	   System.out.println("finally block");
	   if(fis!=null) {
		   
	   
	   try {
		fis.close();
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	   }
	   
   }}
	}

}
