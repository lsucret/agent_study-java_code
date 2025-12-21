package com.example.coordinator;

import com.example.config.ChatModelFactory;
import com.example.model.TravelRequest;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class CoordinatorService {
    
    private final TravelCoordinator coordinator;
    
    public CoordinatorService(ChatModelFactory chatModelFactory) {
        ChatLanguageModel chatModel = chatModelFactory.createChatModel();
        
        this.coordinator = AiServices.builder(TravelCoordinator.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(5))
                .build();
    }
    
    public String planTravel(TravelRequest request) {
        String requestText = String.format(
            "Plan travel from %s to %s, departure: %s, return: %s, budget: %d, travelers: %d, preferences: %s",
            request.origin(), request.destination(), request.departureDate(), 
            request.returnDate(), request.budget(), request.travelers(), request.preferences()
        );
        return coordinator.createTravelPlan(requestText);
    }
}