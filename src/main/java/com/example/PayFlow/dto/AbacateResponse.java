package com.example.PayFlow.dto;

public record AbacateResponse<T>(
        T data,
        String error
) {}
