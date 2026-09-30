package org.elivaas.tests;

import org.elivaas.pages.PaymentPage;
import org.testng.annotations.Test;

/**
 * GATED — disabled by default.
 *
 * Real payment must NEVER be submitted automatically. This test exists only
 * as a manually-enabled stub for read-only verification of the Juspay payment
 * page (payments.juspay.in/payment-page/order/ordv2_&lt;order-id&gt;):
 * "Payable Amount" present, bank-offer banner visible, "secured by JUSPAY"
 * badge visible and the "Proceed to Pay" button DISABLED.
 *
 * To enable manually (never in CI): reach the payment page by hand, then run
 * this test against that session with enabled=true. It enters no card
 * details and submits nothing.
 */
public class PaymentPageTest extends TestBasic {

    @Test(description = "gated: read-only verification of the juspay payment page (never submits payment)",
            enabled = false)
    public void verifyPaymentPageReadOnlyState() {
        PaymentPage paymentPage = new PaymentPage(getDriver());
        paymentPage.assertSafeReadOnlyState();
        System.out.println("Payable amount shown: " + paymentPage.payableAmount());
    }
}
