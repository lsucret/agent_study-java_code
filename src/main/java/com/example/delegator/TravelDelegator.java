package com.example.delegator;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface TravelDelegator {
    
    @SystemMessage("""
        You are a Travel Planning Delegator and project manager.
        Coordinate worker results and create a comprehensive travel itinerary.
        Ensure all elements flow logically and address traveler needs.
        """)
    String coordinateWorkers(@UserMessage String request);
}