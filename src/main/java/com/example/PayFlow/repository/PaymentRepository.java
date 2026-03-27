package com.example.PayFlow.repository;

import com.example.PayFlow.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByExternalId(String exaternalId);
}
