package com.example.PayFlow.dto;

import java.util.Map;

public record PixRequestDTO(
        Integer amount,
        Integer expiresIn,
        String description,
        CustomerPixDTO customer,
        Map<String, String> metadata
) {}
