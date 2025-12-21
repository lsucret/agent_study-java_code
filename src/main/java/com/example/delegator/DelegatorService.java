package com.example.delegator;

import com.example.config.ChatModelFactory;
import com.example.model.WorkerResult;
import com.example.worker.*;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DelegatorService {
    
    private final TravelDelegator delegator;
    private final FlightWorkerService flightWorker;
    private final HotelWorkerService hotelWorker;
    
    public DelegatorService(ChatModelFactory chatModelFactory, 
                           FlightWorkerService flightWorker,
                           HotelWorkerService hotelWorker) {
        this.flightWorker = flightWorker;
        this.hotelWorker = hotelWorker;
        
        ChatLanguageModel chatModel = chatModelFactory.createChatModel();
        
        this.delegator = AiServices.builder(TravelDelegator.class)
                .chatLanguageModel(chatModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(5))
                .build();
    }
    
    public String delegateAndCoordinate(String plan, String origin, String destination, 
                                      String departureDate, String returnDate, 
                                      String budget, String preferences) {
        
        List<CompletableFuture<WorkerResult>> futures = new ArrayList<>();
        
        // 병렬로 Worker들 실행
        futures.add(CompletableFuture.supplyAsync(() -> 
            new WorkerResult("flight", 
                flightWorker.searchFlights(origin, destination, departureDate, preferences), 
                true)));
        
        futures.add(CompletableFuture.supplyAsync(() -> 
            new WorkerResult("hotel", 
                hotelWorker.searchHotels(destination, departureDate, returnDate, budget, preferences), 
                true)));
        
        // 모든 Worker 결과 수집
        List<WorkerResult> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();
        
        // 결과를 문자열로 포맷팅
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("Plan: ").append(plan).append("\n\n");
        requestBuilder.append("Worker Results:\n");
        for (WorkerResult result : results) {
            requestBuilder.append("- ").append(result.workerType()).append(": ").append(result.result()).append("\n");
        }
        
        return delegator.coordinateWorkers(requestBuilder.toString());
    }
}