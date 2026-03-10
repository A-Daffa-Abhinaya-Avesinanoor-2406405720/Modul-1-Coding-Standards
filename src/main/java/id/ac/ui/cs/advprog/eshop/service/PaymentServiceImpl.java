package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = evaluateStatus(method, paymentData);
        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, status, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if (STATUS_SUCCESS.equals(status)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (STATUS_REJECTED.equals(status)) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private String evaluateStatus(String method, Map<String, String> paymentData) {
        if (method == null) {
            return STATUS_REJECTED;
        }

        String normalizedMethod = method.trim().toUpperCase();
        if (normalizedMethod.contains("VOUCHER")) {
            return isVoucherValid(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
        }
        if (normalizedMethod.contains("BANK")) {
            return isBankTransferDataValid(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
        }
        if (normalizedMethod.contains("CASH") || normalizedMethod.contains("DELIVERY")) {
            return isCashOnDeliveryDataValid(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
        }
        return STATUS_REJECTED;
    }

    private boolean isVoucherValid(Map<String, String> paymentData) {
        String voucherCode = getDataValue(paymentData, "voucherCode");
        if (voucherCode == null) {
            return false;
        }
        if (voucherCode.length() != 16) {
            return false;
        }
        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (char current : voucherCode.toCharArray()) {
            if (Character.isDigit(current)) {
                numericCount += 1;
            }
        }
        return numericCount == 8;
    }

    private boolean isBankTransferDataValid(Map<String, String> paymentData) {
        String bankName = getDataValue(paymentData, "bankName");
        String referenceCode = getDataValue(paymentData, "referenceCode");
        return bankName != null && referenceCode != null;
    }

    private boolean isCashOnDeliveryDataValid(Map<String, String> paymentData) {
        String address = getDataValue(paymentData, "address");
        String deliveryFee = getDataValue(paymentData, "deliveryFee");
        return address != null && deliveryFee != null;
    }

    private String getDataValue(Map<String, String> paymentData, String key) {
        if (paymentData == null) {
            return null;
        }
        String value = paymentData.get(key);
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }
}
