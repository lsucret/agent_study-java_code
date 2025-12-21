package com.example;

import com.example.service.LangChain4jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelPlanner {
    
    private final LangChain4jService langChainService;
    
    @Autowired
    public TravelPlanner(LangChain4jService langChainService) {
        this.langChainService = langChainService;
    }
    
    public String planTravel(String request) {
        return langChainService.chat(request);
    }
}