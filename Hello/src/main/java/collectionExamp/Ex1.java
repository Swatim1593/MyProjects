package collectionExamp;

import java.util.HashSet;
import java.util.Iterator;

public class Ex1 {

	public static void main(String[] args)
	{
		HashSet<String> employeeName=new HashSet<>();
		employeeName.add("Swati");
		employeeName.add("smruti");
		employeeName.add("CB");
		employeeName.add("Mahi");
		
	//reading
		Iterator<String> n=employeeName.iterator();
		while(n.hasNext())
		{
			String name =n.next();
			System.out.println("employee name "+name);
		}
		
		//delete
		if(employeeName.contains("Swati"))
		{
			employeeName.remove("Swati");
			System.out.println("after delete "+employeeName);
		}
		else {
			throw new NoDataFoundExcept("no data present is collection:");
		}
	}
		

	

}
