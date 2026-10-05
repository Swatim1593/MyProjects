package sample3;

public class StackHeapDemo {

	    public static void main(String[] args) {
	        System.out.println("=== Stack vs Heap Allocation Demo ===");
	        
	        // 1. Primitive: Purely lives inside this main method's stack frame
	        int localizedTicketPrice = 450; 
	        
	        // 2. Object Reference: 'scores' variable lives on the Stack frame.
	        // The actual array object containing [99, 88, 92] lives on the shared Heap.
	        int[] scores = new int[] { 99, 88, 92 };

	        System.out.println("Primitive read directly from Stack: " + localizedTicketPrice);
	        System.out.println("Accessing heap array data via stack pointer: " + scores[2]);
	        
	        // When this main method ends, its stack frame collapses.
	        // The array on the heap becomes orphan data and is picked up by Garbage Collection.
	    }
	}


