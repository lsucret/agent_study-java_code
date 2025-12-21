package com.example.worker;

import com.example.config.ChatModelFactory;
import com.example.tool.HotelSearchTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class HotelWorkerService {
    
    private final HotelWorker hotelWorker;
    
    public HotelWorkerService(ChatModelFactory chatModelFactory) {
        ChatLanguageModel chatModel = chatModelFactory.createChatModel();
        
        this.hotelWorker = AiServices.builder(HotelWorker.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(3))
                .tools(new HotelSearchTool())
                .build();
    }
    
    public String searchHotels(String location, String checkIn, String checkOut, String budget, String preferences) {
        String request = String.format(
            "Search hotels in %s from %s to %s. Budget: %s. Preferences: %s",
            location, checkIn, checkOut, budget, preferences
        );
        return hotelWorker.searchAndRecommendHotels(request);
    }
}