package com.example.PayFlow.service;

import com.example.PayFlow.client.AbacatePayClient;
import com.example.PayFlow.dto.CustomerPixDTO;
import com.example.PayFlow.dto.PixRequestDTO;
import com.example.PayFlow.dto.PixResponseDTO;
import com.example.PayFlow.dto.WebhookPayloadDTO;
import com.example.PayFlow.entity.Customer;
import com.example.PayFlow.entity.Payment;
import com.example.PayFlow.enums.Status;
import com.example.PayFlow.repository.CustomerRepository;
import com.example.PayFlow.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AbacatePayClient abacatePayClient;
    private final CustomerRepository customerRepository;

    @Transactional
    public Payment createPayment(Payment payment) {

        Customer fullCustomer = customerRepository.findById(payment.getCustomer().getId())
                        .orElseThrow(() -> new RuntimeException("Cliente não cadastrado"));

        payment.setCustomer(fullCustomer);
        payment.setStatus(Status.PENDING);
        Payment savedPayment = paymentRepository.save(payment);

        PixRequestDTO apiRequest = new PixRequestDTO(
                savedPayment.getAmount(),
                36000,
                "Pagamento PayFlow #" + savedPayment.getId(),
                new CustomerPixDTO(
                        savedPayment.getCustomer().getName(),
                        savedPayment.getCustomer().getCellphone(),
                        savedPayment.getCustomer().getEmail(),
                        savedPayment.getCustomer().getTaxId()
                ),
                Map.of("internal_id", savedPayment.getId())
        );

        PixResponseDTO apiResponse = abacatePayClient.createPix(apiRequest);

        savedPayment.setExternalId(apiResponse.id());
        savedPayment.setPixKey(apiResponse.brCode());
        
        return paymentRepository.save(savedPayment);
    }

    @Transactional
    public void processWebhook(WebhookPayloadDTO payload) {

        Payment payment = paymentRepository.findByExternalId(payload.data().id())
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));

        if ("transparent.completed".equals(payload.event()) || "checkout.completed".equals(payload.event())) {
            payment.setStatus(Status.PAID);
            paymentRepository.save(payment);
            log.info("Pagamento {} atualizado para PAID com sucesso!", payment.getId());
        }
    }
}
