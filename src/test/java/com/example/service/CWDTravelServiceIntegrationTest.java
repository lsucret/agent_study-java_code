package com.example.service;

import com.example.model.TravelRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class CWDTravelServiceIntegrationTest {

    @Autowired
    private CWDTravelService cwdTravelService;

    @Test
    void testRealTravelPlanning() {
        // Given - 실제 여행 계획 요청
        TravelRequest request = new TravelRequest(
            "New York",
            "Paris",
            "2025-05-07", 
            "2025-05-14",
            8000,
            2,
            "Anniversary trip for 2 people. Prefer direct flights with morning departure. " +
            "Hotel budget around $300-400 per night with WiFi. " +
            "Moderate pace activities with some relaxation time. " +
            "Mix of walking and public transit for transportation."
        );

        // When
        String result = cwdTravelService.planTravel(request);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // 결과에 주요 요소들이 포함되어 있는지 확인
        String lowerResult = result.toLowerCase();
        assertTrue(lowerResult.contains("flight") || lowerResult.contains("airline"));
        assertTrue(lowerResult.contains("hotel") || lowerResult.contains("accommodation"));
        
        System.out.println("=== CWD Travel Planning Result ===");
        System.out.println(result);
        System.out.println("=================================");
    }

    @Test 
    void testQuickTravelPlanning() {
        // Given - 간단한 요청
        TravelRequest request = new TravelRequest(
            "San Francisco",
            "London",
            "2025-07-01",
            "2025-07-05", 
            5000,
            1,
            "Business trip, need efficient travel"
        );

        // When
        String result = cwdTravelService.planTravel(request);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        System.out.println("=== Quick Travel Plan ===");
        System.out.println(result);
        System.out.println("========================");
    }
}