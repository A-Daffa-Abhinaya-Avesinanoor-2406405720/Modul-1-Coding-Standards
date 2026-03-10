package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentTest {
    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);
        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("pay-1", order, "Voucher Code", "SUCCESS", paymentData);

        assertEquals("pay-1", payment.getId());
        assertEquals("Voucher Code", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertNotNull(payment.getOrder());
        assertEquals(order.getId(), payment.getOrder().getId());
    }

    @Test
    void testSetStatus() {
        Payment payment = new Payment("pay-1", order, "Voucher Code", "REJECTED", new HashMap<>());

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }
}
