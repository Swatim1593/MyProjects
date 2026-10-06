package Pratice1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ex3 {
	
	
	
	public static void main(String[]args) {
		//int[] num= {10,60,30};
		List<String> list=new ArrayList<>();
		list.add("Appel");
        list.add("mango");
        list.add("Banana");
        Comparator<String> c = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		};
		Collections.sort(list,c);
		for(int i=0 ; i<list.size();i++) {
			System.out.println(list.get(i));
		}
		
		//list.forEach(String s : System.out.println(s););
	}
}


