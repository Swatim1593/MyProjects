package sample;

public class Demo1Literals {
	public static void main(String[]args) {
	int marketCount = 12;
	double  price =99.50;
	System.out.println("item in dabba:" +marketCount);
	System.out.println("price tagged:"+price);
	
	//1.Boolean Literals
	boolean isjavaFun = true;
	boolean ispythonStrict = false;
	
	System.out.println("Boolean True:"+isjavaFun);
	System.out.println("Boolean False:"+ispythonStrict);
	
	//2.Byte and Short Literals
	
	byte explicitByte=120;
	short explicitshort=32000;
	System.out.println("_2.Byte & short(implicit Narrowing)_");
	System.out.println("Byte value:"+explicitByte);
	System.out.println("Short value:"+explicitshort);
	
	//3. Integer Literals
	
	char standardChar='A';
	char unicodeChar='\u0041';
	char devanagariChar='\n';
	char escapeChar='\n';
	
	System.out.println("_3.Integer Literals_");
	System.out.println("Strandard Char:"+standardChar);
	System.out.println("UnicodeChar (\\u0041):"+unicodeChar);
	System.out.println("Indian Devanagari Unicode(\\u0905):"+devanagariChar);
	System.out.println("Tessting Escape Character(line break below):+escapeChar");
	
	//4.Integer Literals
	int decimalInt=1_500_000;
	int binaryInt =0b1010;
	int hexaInt= 0x1A;
	int octalInt=012;
	
	System.out.println("_4.Integer Literals_");
	System.out.println("Decimal Int ="+decimalInt);
	System.out.println("Binary Int ="+binaryInt);
	System.out.println("Hexa Int ="+hexaInt);
	System.out.println("Octal Int="+octalInt);
	System.out.println();
	
	//5.Long Literals
	
	long bigPopulation=1400000000000L;
	long massiveNumber =9999999999999L;
	System.out.println("_5.Long Literals_");
	System.out.println("Standard Long="+ bigPopulation);
	System.out.println("Massive Long="+massiveNumber);
	System.out.println();
	
	//6.Float and Double Literals
	
     double defaultDouble =3.14159;
     double explicityDouble= 2.5d;
     float standardFloat=3.14f;
     
     System.out.println("_Float and Double Literals");
     System.out.println("Default Double:" +defaultDouble);
     System.out.println("Explicit Double:"+ explicityDouble);
     System.out.println();

}
}