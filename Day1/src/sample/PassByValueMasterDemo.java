package sample;


		import java.util.Arrays;

		public class PassByValueMasterDemo {
			int global;
		    public static void main(String[] args) {
		        int localCash = 500;
		        int[] sharedAccountBalances = { 1000, 2000, 3000 };

		        System.out.println("=== 1. Primitive Verification ===");
		        System.out.println("Before method call, localCash = " + localCash);
		        //Method call :already define method
		        tryToModifyPrimitive(localCash);
		        System.out.println("After method call, localCash = " + localCash + " (Unchanged!)");

		        System.out.println("\n=== 2. Object State Modification ===");
		        System.out.println("Balances before modification: " + Arrays.toString(sharedAccountBalances));
		        modifyObjectInternalState(sharedAccountBalances);
		        System.out.println("Balances after modification: " + Arrays.toString(sharedAccountBalances) + " (Mutated!)");

		        System.out.println("\n=== 3. Object Reference Reassignment Isolation ===");
		        System.out.println("Balances before reassignment attempt: " + Arrays.toString(sharedAccountBalances));
		        tryReferenceReassignment(sharedAccountBalances);
		        System.out.println("Balances after reassignment attempt: " + Arrays.toString(sharedAccountBalances) + " (Isolated/Unchanged!)");
		    }

		    //Method defination
		    public static void tryToModifyPrimitive(int cashCopy) {
		        cashCopy = 99999; // Modifies only the temporary stack copy
		    }

		    public static void modifyObjectInternalState(int[] accountTokenCopy) {
		        // Modifies the actual data on the heap using the copied address pointer
		        accountTokenCopy[0] = 0; 
		    }

		    public static void tryReferenceReassignment(int[] accountTokenCopy) {
		        // Point the local parameter copy to an entirely new array object on the heap
		        accountTokenCopy = new int[] { 55, 66, 77 };
		        System.out.println("   -> Inside method, token rewritten to new array: " + Arrays.toString(accountTokenCopy));
		    }
		}
