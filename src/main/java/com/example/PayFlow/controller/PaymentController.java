package com.example.PayFlow.controller;

import com.example.PayFlow.dto.WebhookPayloadDTO;
import com.example.PayFlow.entity.Payment;
import com.example.PayFlow.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Value("$(abacatepay.webhook.secret)")
    private String secretPayFlow;

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveWebhook(
            @RequestBody WebhookPayloadDTO payload,
            @RequestParam("webhookSecret") String secret) {

       if (!secretPayFlow.equals(secret))
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

       paymentService.processWebhook(payload);
       return ResponseEntity.ok().build();
    }

}
