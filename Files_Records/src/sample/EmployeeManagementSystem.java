package sample;

import java.io.*;

//============================================================================
//STEP 1: SERIALIZABLE MODEL CLASS
//============================================================================
class Employee implements Serializable {
 private static final long serialVersionUID = 1L;

 private int id;
 private String name;
 private double salary;

 public Employee(int id, String name, double salary) {
     this.id = id;
     this.name = name;
     this.salary = salary;
 }

 public int getId() { return id; }
 public String getName() { return name; }
 public double getSalary() { return salary; }

 @Override
 public String toString() {
     return id + "," + name + "," + salary;
 }
}

//============================================================================
//STEP 2: CUSTOM CHECKED EXCEPTION
//============================================================================
class InvalidSalaryException extends Exception {
 public InvalidSalaryException(String msg) {
     super(msg);
 }
}

//============================================================================
//STEP 3: CHARACTER STREAM WRITER
//============================================================================
class EmployeeWriter {
 public static void writeData(String filename) throws IOException {
     try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
         bw.write("101,Ram,50000");
         bw.newLine();
         bw.write("102,Sita,60000");
         bw.newLine();
         bw.write("103,John,70000");
         bw.newLine();
     }
     System.out.println("[Step 3] Character Stream: Data successfully saved to " + filename);
 }
}

//============================================================================
//STEP 4: CHARACTER STREAM READER
//============================================================================
class EmployeeReader {
 public static void readData(String filename) throws IOException {
     System.out.println("[Step 4] Character Stream: Reading lines from " + filename + ":");
     try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
         String line;
         while ((line = br.readLine()) != null) {
             System.out.println("  -> " + line);
         }
     }
 }
}

//============================================================================
//STEP 5: FILE NAVIGATION & METADATA
//============================================================================
class FileNavigation {
 public static void inspectFile(String filename) {
     File file = new File(filename);
     System.out.println("[Step 5] File Navigation Metadata:");
     System.out.println("  -> Exists     : " + file.exists());
     System.out.println("  -> File Name  : " + file.getName());
     System.out.println("  -> Abs Path   : " + file.getAbsolutePath());
     System.out.println("  -> File Size  : " + file.length() + " bytes");
 }
}

//============================================================================
//STEP 6: BYTE STREAM BACKUP (FileInputStream / FileOutputStream)
//============================================================================
class FileBackup {
 public static void backup(String sourceFile, String backupFile) throws IOException {
     try (FileInputStream fis = new FileInputStream(sourceFile);
          FileOutputStream fos = new FileOutputStream(backupFile)) {
         int data;
         while ((data = fis.read()) != -1) {
             fos.write(data);
         }
     }
     System.out.println("[Step 6] Byte Stream: Backup created successfully at " + backupFile);
 }
}

//============================================================================
//STEP 7: RANDOM ACCESS FILE (Direct Byte Pointer Seek)
//============================================================================
class RandomAccessService {
 public static void readFromOffset(String filename, long byteOffset) throws IOException {
     try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
         raf.seek(byteOffset);
         String line = raf.readLine();
         System.out.println("[Step 7] RandomAccessFile: Read from byte offset " + byteOffset + " -> " + line);
     }
 }
}

//============================================================================
//STEP 8 & 9: OBJECT SERIALIZATION & DESERIALIZATION
//============================================================================
class SerializationService {
 public static void serializeEmployee(String filename, Employee emp) throws IOException {
     try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
         out.writeObject(emp);
     }
     System.out.println("[Step 8] Serialization: Object serialized to " + filename);
 }

 public static Employee deserializeEmployee(String filename) throws IOException, ClassNotFoundException {
     try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
         Employee emp = (Employee) in.readObject();
         System.out.println("[Step 9] Deserialization: Restored object -> " + emp);
         return emp;
     }
 }
}

//============================================================================
//STEP 11: SALARY VALIDATION USING CUSTOM EXCEPTION
//============================================================================
class SalaryValidator {
 public static void validateSalary(double salary) throws InvalidSalaryException {
     if (salary < 0) {
         throw new InvalidSalaryException("Salary cannot be negative: $" + salary);
     }
     System.out.println("[Step 11] Custom Exception Check: Salary $" + salary + " is valid.");
 }
}

//============================================================================
//STEP 12 & 13: PROPAGATION AND RETHROWING
//============================================================================
class PropagationAndRethrowService {
 private static void lowLevelOperation() {
     int x = 10 / 0; // Triggers ArithmeticException
 }

 public static void midLevelLayer() {
     lowLevelOperation(); // Propagates uncaught up the call stack
 }

 public static void testRethrow() {
     try {
         int x = 10 / 0;
     } catch (ArithmeticException e) {
         System.out.println("[Step 13] Rethrowing Demo: Caught locally, logging trace...");
         throw e; // Rethrowing to outer handler
     }
 }
}

//============================================================================
//STEP 14: MASTER DRIVER PROGRAM WITH MULTI-CATCH & FINALLY
//============================================================================
public class EmployeeManagementSystem {

 public static void main(String[] args) {
     String empFile = "employees.txt";
     String backupFile = "backup.txt";
     String serFile = "employee.ser";

     System.out.println("=================================================");
     System.out.println("   EMPLOYEE MANAGEMENT SYSTEM - WORKFLOW DEMO   ");
     System.out.println("=================================================\n");

     try {
         // 1. Character Stream: Write & Read
         EmployeeWriter.writeData(empFile);
         EmployeeReader.readData(empFile);

         // 2. File Metadata Inspection
         System.out.println();
         FileNavigation.inspectFile(empFile);

         // 3. Byte Stream: Binary File Backup
         System.out.println();
         FileBackup.backup(empFile, backupFile);

         // 4. Random Access File (Direct Byte Pointer Navigation)
         System.out.println();
         RandomAccessService.readFromOffset(empFile, 15);

         // 5. Object Serialization & Deserialization
         System.out.println();
         Employee emp1 = new Employee(101, "Ram", 50000.0);
         SerializationService.serializeEmployee(serFile, emp1);
         SerializationService.deserializeEmployee(serFile);

         // 6. Custom Exception Handling
         System.out.println();
         SalaryValidator.validateSalary(45000.0);
         try {
             SalaryValidator.validateSalary(-5000.0);
         } catch (InvalidSalaryException e) {
             System.err.println("[Step 11 Handled] Custom Business Error: " + e.getMessage());
         }

         // 7. Exception Propagation across Stack Frames
         System.out.println();
         try {
             PropagationAndRethrowService.midLevelLayer();
         } catch (ArithmeticException e) {
             System.out.println("[Step 12 Handled] Call Stack Propagation caught at main level: " + e.getMessage());
         }

         // 8. Rethrowing an Exception
         System.out.println();
         try {
             PropagationAndRethrowService.testRethrow();
         } catch (ArithmeticException e) {
             System.out.println("[Step 13 Handled] Outer Handler captured rethrown exception: " + e.getMessage());
         }

     } 
     // --------------------------------------------------------------------
     // POLYMORPHIC MULTI-CATCH HIERARCHY (Specific -> Generic)
     // --------------------------------------------------------------------
     catch (FileNotFoundException e) {
         System.err.println("[HANDLED SPECIFIC] File not found: " + e.getMessage());
     } 
     catch (IOException e) {
         System.err.println("[HANDLED GENERAL I/O] I/O Failure: " + e.getMessage());
     } 
     catch (ClassNotFoundException e) {
         System.err.println("[HANDLED CLASS ERROR] Class not found during deserialization: " + e.getMessage());
     } 
     catch (Exception e) {
         System.err.println("[HANDLED TOP-LEVEL] General System Fault: " + e.getMessage());
     } 
     // --------------------------------------------------------------------
     // GUARANTEED CLEANUP BLOCK
     // --------------------------------------------------------------------
     finally {
         System.out.println("\n-------------------------------------------------");
         System.out.println("[FINALLY] Cleaning up generated demo files...");
         new File(empFile).delete();
         new File(backupFile).delete();
         new File(serFile).delete();
         System.out.println("[FINALLY] All storage handles and demo files cleared.");
         System.out.println("-------------------------------------------------");
     }

     System.out.println("\n=================================================");
     System.out.println("       APPLICATION EXECUTED SUCCESSFULLY         ");
     System.out.println("=================================================");
 }
}