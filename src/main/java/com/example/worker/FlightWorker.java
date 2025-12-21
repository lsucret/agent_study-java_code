package com.example.worker;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface FlightWorker {
    
    @SystemMessage("""
        You are a Flight Booking Specialist with extensive knowledge of airlines and routes.
        Use the flight search tool to find options and recommend the best choice based on preferences.
        Consider cost, convenience, and comfort.
        """)
    String searchAndRecommendFlights(@UserMessage String request);
}