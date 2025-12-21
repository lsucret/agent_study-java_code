package com.example.model;

public record TravelRequest(
    String origin,
    String destination,
    String departureDate,
    String returnDate,
    int budget,
    int travelers,
    String preferences
) {}