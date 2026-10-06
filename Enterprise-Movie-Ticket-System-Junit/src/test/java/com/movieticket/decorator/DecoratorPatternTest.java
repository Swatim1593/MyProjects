package com.movieticket.decorator;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DecoratorPatternTest {

    @Test
    @DisplayName("Verify individual and stacked decorators for pricing and descriptions")
    void testDecorators() {
        TicketComponent base = new BaseTicket("[A1, A2]", 400.0);
        assertEquals(400.0, base.getCost());
        assertEquals("Base Seats [A1, A2] [₹400.0]", base.getDescription());

        TicketComponent withInsurance = new CancellationInsuranceDecorator(base);
        assertEquals(420.0, withInsurance.getCost());
        assertTrue(withInsurance.getDescription().contains("Ticket Insurance [₹20]"));

        TicketComponent withGlasses = new Glasses3DDecorator(base);
        assertEquals(430.0, withGlasses.getCost());
        assertTrue(withGlasses.getDescription().contains("3D Glasses Rental [₹30]"));

        TicketComponent withPopcorn = new PopcornComboDecorator(base);
        assertEquals(550.0, withPopcorn.getCost());
        assertTrue(withPopcorn.getDescription().contains("Popcorn Combo [₹150]"));

        // All stacked together
        TicketComponent fullCombo = new CancellationInsuranceDecorator(
                new Glasses3DDecorator(new PopcornComboDecorator(base)));
        assertEquals(600.0, fullCombo.getCost());
        assertTrue(fullCombo.getDescription().contains("Popcorn Combo"));
        assertTrue(fullCombo.getDescription().contains("3D Glasses Rental"));
        assertTrue(fullCombo.getDescription().contains("Ticket Insurance"));
    }
}