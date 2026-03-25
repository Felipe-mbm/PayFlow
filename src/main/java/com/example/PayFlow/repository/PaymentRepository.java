package com.example.PayFlow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentRepository, String> {
}
