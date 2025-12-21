package com.example.service;

import com.example.config.ChatModelFactory;
import com.example.tool.ActivitySearchTool;
import com.example.tool.FlightSearchTool;
import com.example.tool.HotelSearchTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LangChain4jService {
    
    private final TravelAgent travelAgent;
    
    @Autowired
    public LangChain4jService(ChatModelFactory chatModelFactory) {
        ChatLanguageModel chatModel = chatModelFactory.createChatModel();
        
        this.travelAgent = AiServices.builder(TravelAgent.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .tools(new FlightSearchTool(), new HotelSearchTool(), new ActivitySearchTool())
                .build();
    }
    
    public String chat(String message) {
        return travelAgent.planTravel(message);
    }
    
    interface TravelAgent {
        String planTravel(String request);
    }
}