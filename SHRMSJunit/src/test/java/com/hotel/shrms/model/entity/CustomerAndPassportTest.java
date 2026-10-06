package com.hotel.shrms.model.entity;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.hotel.shmrs.model.entity.Customer;
import com.hotel.shmrs.model.entity.HotelService;
import com.hotel.shmrs.model.entity.Passport;

class CustomerAndPassportTest {

    @Test
    @DisplayName("Should verify customer and passport constructor assignments and getters")
    void testCustomerAndPassportGetters() {
        Passport passport = new Passport("L982341", "IND");
        Customer customer = new Customer("C-101", "Arjun Verma", "arjun@example.com", false, passport, "PIN_8899");

        assertAll("Customer and Passport Properties",
            () -> assertEquals("C-101", customer.getCustomerId()),
            () -> assertEquals("Arjun Verma", customer.getName()),
            () -> assertEquals("arjun@example.com", customer.getEmail()),
            () -> assertFalse(customer.isVIP()),
            () -> assertEquals("PIN_8899", customer.getMaskedSecretPin()),
            () -> assertNotNull(customer.getPassport()),
            () -> assertEquals("L982341", customer.getPassport().getPassportNumber()),
            () -> assertEquals("IND", customer.getPassport().getCountryCode())
        );
    }

    @Test
    @DisplayName("Should successfully add and retrieve opted hotel services")
    void testOptedServices() {
        Passport passport = new Passport("K112233", "IND");
        Customer customer = new Customer("C-102", "Priya Sharma", "priya@example.com", true, passport, "PIN_4411");

        customer.addService(new HotelService("Luxury Airport Cab", 1800.0));
        customer.addService(new HotelService("Spa & Sauna Access", 2500.0));

        List<HotelService> services = customer.getOptedServices();
        assertAll("Opted Services Assertions",
            () -> assertEquals(2, services.size()),
            () -> assertEquals("Luxury Airport Cab", services.get(0).getServiceName()),
            () -> assertEquals(1800.0, services.get(0).getCost()),
            () -> assertEquals("Spa & Sauna Access", services.get(1).getServiceName()),
            () -> assertEquals(2500.0, services.get(1).getCost())
        );
    }

    @Nested
    @DisplayName("Object Contracts & Comparisons")
    class CustomerContractsTest {

        @Test
        @DisplayName("Should satisfy equals and hashCode based on customerId")
        void testEqualsAndHashCode() {
            Passport p1 = new Passport("P1", "IND");
            Passport p2 = new Passport("P2", "USA");

            Customer c1 = new Customer("C-100", "Same-ID", "a@a.com", false, p1, "PIN_1");
            Customer c2 = new Customer("C-100", "Different", "b@b.com", true, p2, "PIN_2");
            Customer c3 = new Customer("C-200", "Different-ID", "c@c.com", false, p1, "PIN_1");

            assertAll("Equals Contract",
                () -> assertEquals(c1, c1),
                () -> assertEquals(c1, c2),
                () -> assertEquals(c1.hashCode(), c2.hashCode()),
                () -> assertNotEquals(c1, c3),
                () -> assertFalse(c1.equals(null)),
                () -> assertFalse(c1.equals("Some String"))
            );
        }

        @Test
        @DisplayName("Should verify Comparable implementation based on customerId")
        void testCompareTo() {
            Passport p = new Passport("P101", "IND");
            Customer c1 = new Customer("C-101", "Arjun", "a@a.com", false, p, "PIN_1");
            Customer c2 = new Customer("C-102", "Priya", "b@b.com", true, p, "PIN_2");

            assertTrue(c1.compareTo(c2) < 0);
            assertTrue(c2.compareTo(c1) > 0);
            assertEquals(0, c1.compareTo(new Customer("C-101", "Other", "o@o.com", true, p, "PIN_3")));
        }

        @Test
        @DisplayName("Should verify toString output format")
        void testToStringFormat() {
            Passport passport = new Passport("L982341", "IND");
            Customer customerWithPin = new Customer("C-101", "Arjun", "a@a.com", false, passport, "PIN_8899");
            Customer customerNoPin = new Customer("C-102", "Priya", "p@p.com", true, passport, null);

            assertEquals("Passport[L982341 (IND)]", passport.toString());
            assertTrue(customerWithPin.toString().contains("SecretPIN=PIN_8899"));
            assertTrue(customerNoPin.toString().contains("SecretPIN=NULL/PROTECTED"));
        }
    }
}