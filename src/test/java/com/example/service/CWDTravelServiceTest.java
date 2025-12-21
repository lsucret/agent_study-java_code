package com.example.service;

import com.example.model.TravelRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "openai.api.key=test-key",
    "ai.provider=openai"
})
class CWDTravelServiceTest {

    @Autowired
    private CWDTravelService cwdTravelService;

    @Test
    void testPlanTravel() {
        // Given
        TravelRequest request = new TravelRequest(
            "New York",
            "Paris", 
            "2025-05-07",
            "2025-05-14",
            8000,
            2,
            "Anniversary trip, moderate pace, prefer direct flights"
        );

        // When
        String result = cwdTravelService.planTravel(request);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        System.out.println("Travel Plan Result: " + result);
    }

    @Test
    void testPlanTravelWithDifferentDestination() {
        // Given
        TravelRequest request = new TravelRequest(
            "Los Angeles",
            "Tokyo",
            "2025-06-01", 
            "2025-06-10",
            10000,
            1,
            "Business trip, need good hotels near city center"
        );

        // When
        String result = cwdTravelService.planTravel(request);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}