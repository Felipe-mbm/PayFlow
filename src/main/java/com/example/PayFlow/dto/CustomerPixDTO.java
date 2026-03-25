package com.example.PayFlow.dto;

public record CustomerPixDTO(
        String name,
        String cellphone,
        String email,
        String taxId
) {}