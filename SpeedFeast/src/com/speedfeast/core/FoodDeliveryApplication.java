package com.speedfeast.core;

public class FoodDeliveryApplication {

	    public static void main(String[] args) {
	        System.out.println("====================================================");
	        System.out.println("       PROJECT SPEEDFEAST ENGINE SIMULATOR v1.0     ");
	        System.out.println("====================================================\n");

	        // -----------------------------------------------------------------
	        // 1. DATATYPES, LITERALS & VARIABLES DECLARATIONS
	        // -----------------------------------------------------------------
	        byte scaleLevel = 5;
	        short runningYear = 2026;
	        int mockId = 8801;
	        long telemetryContact = 9876543210L;
	        float baseMetrics = 4.88F;
	        double initialFunding = 6500.75;
	        char menuTag = 'V'; // Vegetarian classification literal
	        boolean activeStatus = true;
	        
	        int systemDiscountCode;
	        systemDiscountCode = 75;

	        System.out.println("System Variable Trace Output: Discount = ₹" + systemDiscountCode);

	        // -----------------------------------------------------------------
	        // 2. STRING CLASS UTILITIES DEMONSTRATION
	        // -----------------------------------------------------------------
	        String clientIdentity = "Rahul Sharma";
	        System.out.println("Name Length: " + clientIdentity.length());
	        System.out.println("Upper Transformation: " + clientIdentity.toUpperCase());
	        System.out.println("Lower Transformation: " + clientIdentity.toLowerCase());
	        System.out.println("Extraction Block (0-5): " + clientIdentity.substring(0, 5));
	        System.out.println("Equivalence Comparison Validation: " + clientIdentity.equals("Rahul Sharma"));

	        // -----------------------------------------------------------------
	        // 3. ARRAY MANIPULATION, CONSTRUCTION & LOOPS
	        // -----------------------------------------------------------------
	        
	        
	        System.out.println("\n--- Instantiating Menu Arrays ---");
	        FoodItem[] checkoutCart = new FoodItem[3];

	        checkoutCart[0] = new FoodItem(401, "Hyderabadi Biryani", 290.0);
	        checkoutCart[1] = new FoodItem(402, "Paneer Tikka Pizza", 340.0);
	        checkoutCart[2] = new FoodItem(403, "Crunchy Veg Burger", 120.0);

	        System.out.println("\n[Iteration] Printing Current Cart Elements via standard FOR Loop:");
	        for (int i = 0; i < checkoutCart.length; i++) {
	            System.out.println(" -> Item Target Index (" + i + "): " + checkoutCart[i].getFoodName());
	        }

	        System.out.println("\n[Iteration] Diagnostic run tracking via WHILE loop:");
	        int whileCounter = 1;
	        while (whileCounter <= 2) {
	            System.out.println("   While Marker Value: " + whileCounter);
	            whileCounter++;
	        }

	        System.out.println("\n[Iteration] Diagnostic run tracking via DO-WHILE loop:");
	        int doCounter = 1;
	        do {
	            System.out.println("   Do-While Marker Value: " + doCounter);
	            doCounter++;
	        } while (doCounter <= 2);

	        // -----------------------------------------------------------------
	        // 4. JUMP STATEMENTS (BREAK & CONTINUE)
	        // -----------------------------------------------------------------
	        System.out.println("\n[Control Flow] Evaluating breaking threshold loops (Break at index 4):");
	        for (int k = 1; k <= 6; k++) {
	            if (k == 4) {
	                break;
	            }
	            System.out.println("    Active loop step: " + k);
	        }

	        System.out.println("\n[Control Flow] Evaluating skips execution loops (Skip index 2):");
	        for (int m = 1; m <= 4; m++) {
	            if (m == 2) {
	                continue;
	            }
	            System.out.println("    Active loop step: " + m);
	        }

	        // -----------------------------------------------------------------
	        // 5. MATH & LOGICAL OPERATORS + CONDITIONAL SWITCH
	        // -----------------------------------------------------------------
	        int baseValA = 25;
	        int baseValB = 5;
	        System.out.println("\nBasic Arithmetic Proof: Sum = " + (baseValA + baseValB) + ", Remainder = " + (baseValA % baseValB));
	        System.out.println("Boolean Comparisons: " + (baseValA > baseValB) + " && " + (baseValB == 5) + " Is: " + (baseValA > baseValB && baseValB == 5));

	        // Ternary Assignment Execution
	        String allocationResponse = (baseValA > 20) ? "Premium High Priority Route" : "Standard Route";
	        System.out.println("Ternary Resolution String: " + allocationResponse);

	        int cuisineSelectionIndex = 3;
	        System.out.print("Switch Resolution Result: ");
	        switch (cuisineSelectionIndex) {
	            case 1:
	                System.out.println("Selected Menu: Continental");
	                break;
	            case 2:
	                System.out.println("Selected Menu: Pan-Asian");
	                break;
	            case 3:
	                System.out.println("Selected Menu: Authentic Indian Delicacies");
	                break;
	            default:
	                System.out.println("Selected Menu: Multi-Cuisine Mix");
	        }

	        // -----------------------------------------------------------------
	        // 6. ENTERPRISE BUSINESS FLOW CORE SIMULATION
	        // -----------------------------------------------------------------
	        
	        
	        System.out.println("\n====================================================");
	        System.out.println("        EXECUTING CLIENT LIVE ORDER SIMULATIONS     ");
	        System.out.println("====================================================");
	        
	        
	        
	        
	        
	        // Instantiate domain models
	        Customer liveCustomer = new Customer(1102, "Rahul Sharma", 800.0,true);
	        System.out.println("User Profile Initialized: " + liveCustomer.getCustomerName() + 
	                           " | Wallet Balance: ₹" + liveCustomer.getwalletBalance() + 
	                           " | Prime Member Status: " + liveCustomer.isPrimeMember());

	        // Process Flow using Wallet Strategy (Loose Coupling Injection)
	        Payment walletStrategy = new WalletPayment();
	        OrderService primeOrderPipeline = new OrderService(walletStrategy);
	        
	        System.out.println("\nProcessing Order Request 1 (Using Native Digital Wallet Balance)...");
	        Order completePrimeOrder = primeOrderPipeline.placeOrder(liveCustomer, checkoutCart);

	        System.out.println("\n>>> --- TRANSACTION AUDIT REPORT --- <<<");
	        System.out.println("Final Gross Billings Amount Processed: ₹" + completePrimeOrder.getAmount());
	        System.out.println("Order Lifecycle State Result: " + completePrimeOrder.getstatus());
	        System.out.println("Post Transaction Wallet Balance Left: ₹" + liveCustomer.getwalletBalance());

	        // Proving Loose Coupling capability live by injecting a different strategy (UPI Route)
	        System.out.println("\nProcessing Order Request 2 (Switching Strategy to UPI Route)...");
	        Payment upiStrategy = new UPIPayment("rahul@axisbank");
	        OrderService upiOrderPipeline = new OrderService(upiStrategy);
	        
	        Order fallbackUpiOrder = upiOrderPipeline.placeOrder(liveCustomer, checkoutCart);
	        System.out.println("Transaction Status for Order 2: " + fallbackUpiOrder.getstatus());
	        
	        
	        
	        

	        // -----------------------------------------------------------------
	        // 7. EXCEPTION ROBUSTNESS CHECK & PASS-BY-VALUE SANITY VERIFICATION
	        // -----------------------------------------------------------------
	        System.out.println("\n--- Running Framework Fault Tolerance System Isolation Checks ---");
	        try {
	            int mathematicalAnomaly = 45 / 0;
	        } catch (ArithmeticException exceptionObject) {
	            System.out.println("[HANDLED] Trapped runtime arithmetic error inside critical block safely.");
	        }

	        System.out.println("\n--- Running Verification of Pass-By-Value Mechanics ---");
	        int localizedVoucherValue = 150;
	        System.out.println("Before passing to local processing stack frame, primitive balance = " + localizedVoucherValue);
	        modifyVoucherValue(localizedVoucherValue);
	        System.out.println("After return from execution context frame, primitive balance = " + localizedVoucherValue);
	        System.out.println("Reason: System stack isolated primitive mutations from master reference value.");
	        
	        System.out.println("\n====================================================");
	        System.out.println("         END OF ENTERPRISE ENGINE SIMULATION         ");
	        System.out.println("====================================================");
	    }

	    public static void modifyVoucherValue(int functionalAmountCopy) {
	        functionalAmountCopy = 999; // Value change remains confined purely inside this stack segment
	    }
	}


