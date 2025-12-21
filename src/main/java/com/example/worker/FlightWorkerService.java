package com.example.worker;

import com.example.config.ChatModelFactory;
import com.example.tool.FlightSearchTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class FlightWorkerService {
    
    private final FlightWorker flightWorker;
    
    public FlightWorkerService(ChatModelFactory chatModelFactory) {
        ChatLanguageModel chatModel = chatModelFactory.createChatModel();
        
        this.flightWorker = AiServices.builder(FlightWorker.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(3))
                .tools(new FlightSearchTool())
                .build();
    }
    
    public String searchFlights(String origin, String destination, String date, String preferences) {
        String request = String.format(
            "Search flights from %s to %s on %s. Preferences: %s",
            origin, destination, date, preferences
        );
        return flightWorker.searchAndRecommendFlights(request);
    }
}