package com.example.service;

import com.example.model.TravelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LangChain4jService {
    
    private final CWDTravelService cwdTravelService;
    
    @Autowired
    public LangChain4jService(CWDTravelService cwdTravelService) {
        this.cwdTravelService = cwdTravelService;
    }
    
    public String chat(String message) {
        // 간단한 파싱 (실제로는 더 정교한 파싱 필요)
        TravelRequest request = new TravelRequest(
            "New York", "Paris", "2025-05-07", "2025-05-14", 
            8000, 2, message
        );
        
        return cwdTravelService.planTravel(request);
    }
}