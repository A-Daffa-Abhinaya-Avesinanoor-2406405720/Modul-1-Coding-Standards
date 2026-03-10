package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

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
    void testAddPaymentVoucherValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment result = paymentService.addPayment(order, "Voucher Code", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertNotNull(result.getId());
        assertEquals("Voucher Code", result.getMethod());
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID");

        Payment result = paymentService.addPayment(order, "Voucher Code", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "INV-001");

        Payment result = paymentService.addPayment(order, "Bank Transfer", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentBankTransferInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "INV-001");

        Payment result = paymentService.addPayment(order, "Bank Transfer", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("pay-1", order, "Voucher Code", "REJECTED", new HashMap<>());

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Payment payment = new Payment("pay-1", order, "Voucher Code", "SUCCESS", new HashMap<>());

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("pay-1", order, "Voucher Code", "SUCCESS", new HashMap<>());
        doReturn(payment).when(paymentRepository).findById("pay-1");

        Payment result = paymentService.getPayment("pay-1");

        assertEquals("pay-1", result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("pay-1", order, "Voucher Code", "SUCCESS", new HashMap<>()));
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> results = paymentService.getAllPayments();

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }
}
