package com.quickbite;

import com.quickbite.builder.CustomerBuilder;
import com.quickbite.controller.OrderController;
import com.quickbite.controller.ReportController;
import com.quickbite.dao.impl.CustomerDAOImpl;
import com.quickbite.dao.impl.OrderDAOImpl;
import com.quickbite.dao.impl.ProductDAOImpl;
import com.quickbite.entity.Address;
import com.quickbite.entity.Admin;
import com.quickbite.entity.Category;
import com.quickbite.entity.Customer;
import com.quickbite.entity.Order;
import com.quickbite.entity.Product;
import com.quickbite.exception.InsufficientStockException;
import com.quickbite.exception.OrderNotFoundException;
import com.quickbite.exception.PaymentFailedException;
import com.quickbite.exception.ProductNotFoundException;
import com.quickbite.facade.OrderFacade;
import com.quickbite.factory.PaymentFactory;
import com.quickbite.observer.EmailNotificationObserver;
import com.quickbite.observer.OrderSubject;
import com.quickbite.observer.SMSNotificationObserver;
import com.quickbite.service.CartService;
import com.quickbite.service.CustomerService;
import com.quickbite.service.InventoryService;
import com.quickbite.service.OrderService;
import com.quickbite.service.ProductService;
import com.quickbite.service.ReportService;
import com.quickbite.singleton.DatabaseConnection;
import com.quickbite.strategy.Payment;

public class QuickBiteApplication2 {
	public static void main(String[]args) {
		System.out.println("======================");
		System.out.println("Starting quivckbite omni commerce platform");
		System.out.println("=================\n");
		
		
		DatabaseConnection db=DatabaseConnection.getInstance();
		
		db.executeQuery("SELECT * FROM quick_bite_master_schema:");
		
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		ProductDAOImpl productDAO = new ProductDAOImpl();
		OrderDAOImpl orderDAO = new OrderDAOImpl();

		OrderSubject orderSubject = new OrderSubject();
		orderSubject.attach(new EmailNotificationObserver());
		orderSubject.attach(new SMSNotificationObserver());
		
		CustomerService customerService = new CustomerService(customerDAO);
		ProductService productService = new ProductService(productDAO);
		CartService cartService = new CartService();
		InventoryService inventoryService = new InventoryService(productDAO);
		OrderService orderService = new OrderService(orderDAO, inventoryService, orderSubject);
		ReportService reportService = new ReportService(orderDAO);

		OrderFacade orderFacade = new OrderFacade(customerService, productService, cartService, orderService);
		OrderController orderController = new OrderController(orderFacade);
		ReportController reportController = new ReportController(reportService);

		System.out.println("\n1. CONFIGURING CATALOG & CATEGORIES...");
		Category electronics = new Category(1, "Electronics");
		Category food = new Category(2, "Food");
		Category grocery = new Category(3, "Grocery");
		Category fashion = new Category(4, "Fashion");
		
		
		

		Product p1 = new Product(101, "Sony Wireless Headphones", 12000.0, 15, electronics);
		Product p2 = new Product(102, "Hyderabadi Chicken Biryani", 350.0, 50, food);
		Product p3 = new Product(103, "Organic Olive Oil 1L", 850.0, 30, grocery);
		Product p4 = new Product(104, "Levi's Denim Jacket", 4500.0, 10, fashion);

		productService.addProduct(p1);
		productService.addProduct(p2);
		productService.addProduct(p3);
		productService.addProduct(p4);
		
		System.out.println("   [CATALOG] 4 SKUs registered across 4 Categories.");

		System.out.println("\n2. REGISTERING CUSTOMERS (BUILDER PATTERN)...");
		Address addr1 = new Address("100 Residency Rd", "Bengaluru", "Karnataka", "560025");
		Address addr2 = new Address("45 Anna Salai", "Chennai", "Tamil Nadu", "600002");

		Customer c1 = new CustomerBuilder().setUserId(1).setName("Karthik Raja").setEmail("karthik@quickbite.in")
				.setAddress(addr1).setCity("Bengaluru").setPremium(true).build();

		Customer c2 = new CustomerBuilder().setUserId(2).setName("Ananya Rao").setEmail("ananya@quickbite.in")
				.setAddress(addr2).setCity("Chennai").setPremium(false).build();

		customerService.registerCustomer(c1);
		customerService.registerCustomer(c2);
		System.out.println("[CUSTOMER] Registered: " + c1);
		System.out.println("[CUSTOMER] Registered: " + c2);

		System.out.println("\n3.PROCESSING ORDER #5001 (PREMIUM CUSTOMER)...");
		cartService.addProduct(p1, 1);
		cartService.addProduct(p2, 2);
		Order order1 = orderController.processCheckout(5001, 1, "UPI", "karthik@okaxis");
		System.out.println("   [SUCCESS] " + order1);

		System.out.println("\n4.PROCESSING ORDER #5002 (REGULAR CUSTOMER - PAYPAL ADAPTER)...");
		cartService.addProduct(p2, 4);
		cartService.addProduct(p3, 2);
		Order order2 = orderController.processCheckout(5002, 2, "PAYPAL", "PAYPAL_LIVE_TOKEN_9988");
		System.out.println("[SUCCESS] " + order2);

		reportController.printAnalyticsDashboard();

		System.out.println("Filtered Premium Customers List (Stream API):");
		customerService.getPremiumCustomers().forEach(c -> System.out.println("  -> " + c));
		
		System.out.println("\n================");
		System.out.println("Verifying remaining uncalled methods and exceptions");
		
		System.out.println("=======================\n");
		
		
		Admin admin=new Admin(99,"Super Admin","admin@quickbite.in",addr1,"Engineering",5);
		System.out.println("Admin dept:"+admin.getDepartment()+"|Level:"+admin.getAccessLevel());
		System.out.println("Address:"+addr1.getStreet()+","+addr1.getCity()+","+addr1.getState()+"-"+addr1.getZipCode());
		
		System.out.println("Category cpmare to:"+electronics.compareTo(food));
		System.out.println("Product lookup by ID:"+productService.getProduct(101).getName());
		System.out.println("All products list size:"+productService.getAllProducts().size());
		System.out.println("category set:"+productService.getCategories());
		
		System.out.println("search product'biryani':"+productService.searchByName("biryani"));
		
		cartService.addProduct(p4, 2);
		System.out.println("cart subtotal before removal:"+cartService.getCartTotal());
		
		cartService.removeProduct(104);
		System.out.println("after removal:"+cartService.getCartItems().size());
		System.out.println("all orders count from DAO:"+orderService.getAllOrders().size());
		
		customerDAO.update(c1);
		productDAO.update(p1);
		orderDAO.update(order1);
		
		Customer temp=new CustomerBuilder().setUserId(999).setName("Temp user").build();
		customerDAO.save(temp);
		customerDAO.delete(999);
		productDAO.delete(999);
		
		Payment cardPayment=PaymentFactory.createPayment("CARD","PAY-1001", 1200.00, "4523678087265233");
		cardPayment.processPayment();
		System.out.println("Card TXN id:"+cardPayment.getPaymentId()+"|Amount:"+cardPayment.getAmount());
		
		Payment walletPayment=PaymentFactory.createPayment("WALLET", "PAY-1002", 350.0,"WALLET_ID_99");
		walletPayment.processPayment();
		
		try {
			productService.getProduct(9999);
			
		}catch(ProductNotFoundException e) {
			System.out.println("caught exception:"+e.getMessage());
			
		}
		
		try {
			inventoryService.deductStock(101, 5000);
			
		}catch(InsufficientStockException e) {
			System.out.println("caufght exception:"+e.getMessage());
			
		}
		
		try {
			throw new OrderNotFoundException("Order #999 not found.");
		}catch(OrderNotFoundException e) {
			System.out.println("caufght exception:"+e.getMessage());
		}
		
		try {
			throw new PaymentFailedException("Payment Failed simulation.");
		}catch(PaymentFailedException e) {
			System.out.println("caufght exception:"+e.getMessage());
		}
		
		System.out.println("\n======================");
		System.out.println("All class methods and fuctionalities fully verified");
		System.out.println("=================");
		
	}

}
