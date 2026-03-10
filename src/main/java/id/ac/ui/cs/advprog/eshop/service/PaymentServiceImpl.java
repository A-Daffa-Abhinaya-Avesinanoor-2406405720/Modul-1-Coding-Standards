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
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = "REJECTED";

        if ("Voucher Code".equals(method)) {
            String voucherCode = paymentData == null ? null : paymentData.get("voucherCode");
            boolean isValid = voucherCode != null
                    && voucherCode.length() == 16
                    && voucherCode.startsWith("ESHOP");

            if (isValid) {
                int numericCount = 0;
                for (char current : voucherCode.toCharArray()) {
                    if (Character.isDigit(current)) {
                        numericCount += 1;
                    }
                }
                if (numericCount == 8) {
                    status = "SUCCESS";
                }
            }
        } else if ("Bank Transfer".equals(method)) {
            String bankName = paymentData == null ? null : paymentData.get("bankName");
            String referenceCode = paymentData == null ? null : paymentData.get("referenceCode");
            if (bankName != null && !bankName.isBlank() && referenceCode != null && !referenceCode.isBlank()) {
                status = "SUCCESS";
            }
        } else if ("Cash on Delivery".equals(method)) {
            String address = paymentData == null ? null : paymentData.get("address");
            String deliveryFee = paymentData == null ? null : paymentData.get("deliveryFee");
            if (address != null && !address.isBlank() && deliveryFee != null && !deliveryFee.isBlank()) {
                status = "SUCCESS";
            }
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), order, method, status, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if ("SUCCESS".equals(status)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if ("REJECTED".equals(status)) {
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
}
