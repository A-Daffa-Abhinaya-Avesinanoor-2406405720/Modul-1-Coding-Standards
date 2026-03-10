package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private List<Payment> payments;
    private Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");
        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "INV-001");

        payments = new ArrayList<>();
        payments.add(new Payment("pay-1", order, "Voucher Code", "SUCCESS", voucherData));
        payments.add(new Payment("pay-2", order, "Bank Transfer", "SUCCESS", bankData));
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);

        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("pay-1");

        assertEquals("pay-1", result.getId());
        assertEquals("pay-1", findResult.getId());
        assertEquals("Voucher Code", findResult.getMethod());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment updated = new Payment("pay-1", order, "Voucher Code", "REJECTED", payment.getPaymentData());
        Payment result = paymentRepository.save(updated);
        Payment findResult = paymentRepository.findById("pay-1");

        assertEquals("pay-1", result.getId());
        assertEquals("REJECTED", findResult.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        paymentRepository.save(payments.get(0));

        assertNull(paymentRepository.findById("missing"));
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));

        List<Payment> result = paymentRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("pay-1", result.get(0).getId());
        assertEquals("pay-2", result.get(1).getId());
    }
}
