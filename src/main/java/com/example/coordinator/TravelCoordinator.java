package com.example.coordinator;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface TravelCoordinator {
    
    @SystemMessage("""
        You are a Travel Planning Coordinator with 15 years of experience.
        Create a detailed step-by-step travel plan based on user requests.
        Focus on: flight booking, hotel booking, activities, and local transportation.
        Provide clear, actionable steps for the delegator agent.
        """)
    String createTravelPlan(@UserMessage String request);
}