package com.example.model;

public record TravelPlan(
    String overview,
    String flightPlan,
    String hotelPlan,
    String activityPlan,
    String transportPlan
) {}