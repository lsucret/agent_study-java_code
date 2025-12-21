package com.example.worker;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;

public interface TransportWorker {
    
    @SystemMessage("""
        You are a Local Transportation Coordinator specializing in transportation logistics.
        Arrange efficient and convenient local transportation covering public transit,
        private transfers, and navigation between destinations.
        """)
    String planTransportation(@V("location") String location, @V("origin") String origin, @V("destination") String destination, @V("preferences") String preferences);
}