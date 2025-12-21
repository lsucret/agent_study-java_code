package com.example.worker;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;

public interface ActivityWorker {
    
    @SystemMessage("""
        You are an Activities and Excursions Planner with insider knowledge of attractions.
        Create personalized activity itineraries that align with travelers' interests.
        Consider adventure, culture, relaxation, or culinary experiences.
        """)
    String planActivities(@V("location") String location, @V("dates") String dates, @V("interests") String interests, @V("pace") String pace);
}