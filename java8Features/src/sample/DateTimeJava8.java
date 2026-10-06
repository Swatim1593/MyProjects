package sample;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateTimeJava8 {
    public static void main(String[] args) throws Exception {
        System.out.println("=== JAVA 8 DATE & TIME (IMMUTABLE & THREAD-SAFE) ===");

        // 1. Instantiating Joining Date (Explicit Month Enum, 1-based index)
        LocalDate joiningDate = LocalDate.of(2021, Month.JULY, 15);
        System.out.println("Joining Date"+joiningDate);
        LocalTime shiftStart = LocalTime.of(9, 30, 0);
        System.out.println("Shift time"+shiftStart);
        
        LocalDateTime currentSession = LocalDateTime.now();
        System.out.println("Current Date"+currentSession);

        // 2. Date Arithmetic (IMMUTABLE: Returns a brand-new instance)
        LocalDate reviewDate = joiningDate.plusYears(3);
        System.out.println("Review Date"+reviewDate);
        
        // 3. Calculating Date Difference using Period
        Period tenure = Period.between(joiningDate, LocalDate.now());
        System.out.println("Karthik's Tenure : " + tenure.getYears() + " Years, " 
                + tenure.getMonths() + " Months, " + tenure.getDays() + " Days");

        // 4. Thread-Safe Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        System.out.println("Java 8 Joining Date : " + joiningDate.atTime(shiftStart).format(formatter));
        System.out.println("Java 8 Review Date  : " + reviewDate.atTime(shiftStart).format(formatter));

        // 5. Calculating Time Duration using Duration
        Instant startInstant = Instant.now();
        Thread.sleep(100); // Simulating work
        Instant endInstant = Instant.now();
        Duration elapsed = Duration.between(startInstant, endInstant);
        System.out.println("Java 8 Elapsed Time : " + elapsed.toMillis() + " ms");
    }
}