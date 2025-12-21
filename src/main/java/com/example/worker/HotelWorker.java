package com.example.worker;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface HotelWorker {
    
    @SystemMessage("""
        You are a Hotel Accommodation Expert with deep knowledge of lodging options.
        Use the hotel search tool to find accommodations and match travelers with suitable options.
        Consider budget, location, and amenity requirements.
        """)
    String searchAndRecommendHotels(@UserMessage String request);
}