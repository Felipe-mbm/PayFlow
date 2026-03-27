package com.example.PayFlow.dto;

public record WebhookPayloadDTO(
   String event,
   WebhookDataDTO data
) {}
