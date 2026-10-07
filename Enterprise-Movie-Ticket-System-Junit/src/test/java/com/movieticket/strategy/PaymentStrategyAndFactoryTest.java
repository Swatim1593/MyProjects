package com.movieticket.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.movieticket.enums.PaymentMethod;

class PaymentStrategyAndFactoryTest {

    @Test
    @DisplayName("Factory returns correct strategy for each PaymentMethod")
    void testPaymentStrategyFactory() {
        PaymentStrategy upi = PaymentStrategyFactory.getStrategy(PaymentMethod.UPI);
        assertNotNull(upi);
        assertTrue(upi instanceof UPIPaymentStrategy);

        PaymentStrategy netBanking = PaymentStrategyFactory.getStrategy(PaymentMethod.NET_BANKING);
        assertNotNull(netBanking);
        assertTrue(netBanking instanceof NetBankingPaymentStrategy);

        PaymentStrategy walletDefault = PaymentStrategyFactory.getStrategy(PaymentMethod.WALLET);
        assertNotNull(walletDefault);
        assertTrue(walletDefault instanceof UPIPaymentStrategy);
    }

    @Test
    @DisplayName("UPI payment and refund execution return true")
    void testUPIPaymentStrategy() {
        UPIPaymentStrategy upi = new UPIPaymentStrategy();
        assertTrue(upi.processPayment("BK-100", 250.0));
        assertTrue(upi.processRefund("BK-100", 250.0));
    }

    @Test
    @DisplayName("NetBanking payment and refund execution return true")
    void testNetBankingPaymentStrategy() {
        NetBankingPaymentStrategy netBanking = new NetBankingPaymentStrategy();
        assertTrue(netBanking.processPayment("BK-200", 450.0));
        assertTrue(netBanking.processRefund("BK-200", 450.0));
    }
}