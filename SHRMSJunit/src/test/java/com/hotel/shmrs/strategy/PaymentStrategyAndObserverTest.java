package com.hotel.shmrs.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.model.enums.PaymentMode;
import com.hotel.shmrs.observer.EmailNotification;
import com.hotel.shmrs.observer.NotificationObserver;
import com.hotel.shmrs.observer.SMSNotification;

class PaymentStrategyAndObserverTest {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));
	}

	@AfterEach
	void tearDown() {
		System.setOut(originalOut);
	}

	@Test
	@DisplayName("CardPaymentStrategy should execute successfully and mask card number")
	void testCardPaymentSuccess() throws InvalidPaymentException {
		PaymentStrategy card = new CardPaymentStrategy("4532112233448899", PaymentMode.CREDIT_CARD);

		assertTrue(card.executePayment(5500.0));
		assertEquals(PaymentMode.CREDIT_CARD, card.getMode());

		String log = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(log.contains("[Payment: CREDIT_CARD] Charged ₹5500.0 to Card ****-****-****-8899"));
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -10.0, -500.0 })
	@DisplayName("CardPaymentStrategy should throw InvalidPaymentException for non-positive amounts")
	void testCardPaymentInvalidAmount(double invalidAmount) {
		PaymentStrategy card = new CardPaymentStrategy("4532112233448899", PaymentMode.DEBIT_CARD);

		InvalidPaymentException ex = assertThrows(InvalidPaymentException.class,
				() -> card.executePayment(invalidAmount));
		assertEquals("Payment value must be strictly positive.", ex.getMessage());
	}

	@Test
	@DisplayName("UPIPaymentStrategy should execute successfully and print VPA")
	void testUPIPaymentSuccess() throws InvalidPaymentException {
		PaymentStrategy upi = new UPIPaymentStrategy("arjun@okaxis");

		assertTrue(upi.executePayment(3500.0));
		assertEquals(PaymentMode.UPI, upi.getMode());

		String log = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(log.contains("[Payment: UPI] Successfully debited ₹3500.0 via VPA: arjun@okaxis"));
	}

	@ParameterizedTest
	@ValueSource(doubles = { 0.0, -1.0 })
	@DisplayName("UPIPaymentStrategy should throw InvalidPaymentException for non-positive amounts")
	void testUPIPaymentInvalidAmount(double invalidAmount) {
		PaymentStrategy upi = new UPIPaymentStrategy("guest@upi");

		assertThrows(InvalidPaymentException.class, () -> upi.executePayment(invalidAmount));
	}

	@Test
	@DisplayName("EmailNotification observer should print formatted message")
	void testEmailNotification() {
		NotificationObserver emailObs = new EmailNotification();
		emailObs.notifyUser("karthik@example.com", "Booking Confirmed!");

		String log = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(log.contains("[Notification: EMAIL] -> karthik@example.com | Booking Confirmed!"));
	}

	@Test
	@DisplayName("SMSNotification observer should print formatted message")
	void testSMSNotification() {
		NotificationObserver smsObs = new SMSNotification();
		smsObs.notifyUser("9876543210", "OTP: 1234");

		String log = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(log.contains("[Notification: SMS]   -> 9876543210 | OTP: 1234"));
	}
}