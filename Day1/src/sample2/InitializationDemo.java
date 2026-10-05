package sample2;

public class InitializationDemo 
{
	
	    // Heap fields - Automatically initialized by the JVM
	    int defaultInt;
	    String defaultRef;
	    int[] defaultArrayRef;

	    public void runDemo() {
	        System.out.println("=== 1. Heap Field Automatically Initialized ===");
	        System.out.println("Default primitive int: " + defaultInt);       // Output: 0
	        System.out.println("Default Object reference: " + defaultRef);     // Output: null
	        System.out.println("Default Array reference: " + defaultArrayRef); // Output: null

	        System.out.println("\n=== 2. Local Stack Variables ===");
	        int localPrimitive;
	        String localReference;

	        // CRITICAL: Unassigned local reads are strictly blocked by the compiler.
	        // If you uncomment the lines below, the code WILL NOT compile.
	        // System.out.println(localPrimitive); 
	        // System.out.println(localReference);

	        // Once explicitly assigned, they become completely legal to read:
	        localPrimitive = 108;
	        System.out.println("Explicitly assigned local primitive: " + localPrimitive);
	    }

	    public static void main(String[] args) {
	        new InitializationDemo().runDemo();
	    }
	}


