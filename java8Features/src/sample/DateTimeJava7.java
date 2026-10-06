package sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeJava7 {
    public static void main(String[] args) throws Exception {
        System.out.println("=== JAVA 7 DATE & TIME (MUTABLE & ZERO-INDEXED) ===");

        // 1. Instantiating Joining Date (Confusing: Month 6 = July)
        Calendar cal = Calendar.getInstance();
        System.out.println("Calender"+cal);
        cal.set(2021, Calendar.JULY, 15, 9, 30, 0); // 15-July-2021 09:30:00
        Date joiningDate = cal.getTime();
        System.out.println("Joining Date"+joiningDate);
        // 2. Date Arithmetic (MUTATES the internal Calendar instance!)
        cal.add(Calendar.YEAR, 3); // Adding 3 years for review cycle
        Date reviewDate = cal.getTime();
        
        System.out.println("Review Date"+reviewDate);

        // 3. Formatting (SimpleDateFormat is NOT thread-safe)
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        
        System.out.println("Java 7 Joining Date for Karthik : " + sdf.format(joiningDate));
        System.out.println("Java 7 Review Date for Karthik  : " + sdf.format(reviewDate));

        // 4. Time Difference Calculation (Manual Millisecond Math)
        long startMs = System.currentTimeMillis();
        Thread.sleep(100); // Simulating work
        long endMs = System.currentTimeMillis();
        long durationMs = endMs - startMs;
        System.out.println("Java 7 Elapsed Shift Time       : " + durationMs + " ms");
        
    }
}
