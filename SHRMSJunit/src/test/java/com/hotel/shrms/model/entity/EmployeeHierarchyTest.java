package com.hotel.shrms.model.entity;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hotel.shmrs.model.entity.Employee;
import com.hotel.shmrs.model.entity.Manager;
import com.hotel.shmrs.model.entity.Receptionist;

class EmployeeHierarchyTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should verify Manager and Receptionist duties execution")
    void testEmployeeDutiesExecution() {
        Employee manager = new Manager("EMP-101", "Murugan Chettiar", 95000.0);
        Employee receptionist = new Receptionist("EMP-102", "Soundarya Rajinikanth", 48000.0);

        manager.performDuties();
        receptionist.performDuties();

        String output = outputStream.toString(StandardCharsets.UTF_8);

        assertAll("Employee Duties Console Verification",
            () -> assertTrue(output.contains("Manager Murugan Chettiar")),
            () -> assertTrue(output.contains("Authorizing VIP allocations and inventory audits")),
            () -> assertTrue(output.contains("Receptionist Soundarya Rajinikanth")),
            () -> assertTrue(output.contains("Managing guest check-in terminal"))
        );
    }

    @Test
    @DisplayName("Should execute duty for individual staff instances")
    void testIndividualStaffDuty() {
        Manager operationsLead = new Manager("EMP-201", "Karthik Sivakumar", 110000.0);
        operationsLead.performDuties();
        assertTrue(outputStream.toString(StandardCharsets.UTF_8).contains("Karthik Sivakumar"));

        outputStream.reset();

        Receptionist frontDesk = new Receptionist("EMP-202", "Keerthy Suresh", 52000.0);
        frontDesk.performDuties();
        assertTrue(outputStream.toString(StandardCharsets.UTF_8).contains("Keerthy Suresh"));
    }
}