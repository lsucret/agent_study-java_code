package com.example.service;

import com.example.coordinator.CoordinatorService;
import com.example.delegator.DelegatorService;
import com.example.model.TravelRequest;
import org.springframework.stereotype.Service;

@Service
public class CWDTravelService {
    
    private final CoordinatorService coordinator;
    private final DelegatorService delegator;
    
    public CWDTravelService(CoordinatorService coordinator, DelegatorService delegator) {
        this.coordinator = coordinator;
        this.delegator = delegator;
    }
    
    public String planTravel(TravelRequest request) {
        // 1. Coordinator: 전체 계획 수립
        String plan = coordinator.planTravel(request);
        
        // 2. Delegator: 작업 분배 및 결과 통합
        return delegator.delegateAndCoordinate(
            plan,
            request.origin(),
            request.destination(),
            request.departureDate(),
            request.returnDate(),
            String.valueOf(request.budget()),
            request.preferences()
        );
    }
}