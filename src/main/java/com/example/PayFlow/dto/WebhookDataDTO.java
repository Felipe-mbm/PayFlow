package com.example.PayFlow.dto;

import java.util.Map;

public record WebhookDataDTO(
        String id,
        String status,
        Map<String, String> metadata
) {}
