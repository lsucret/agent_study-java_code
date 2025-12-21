package com.example.delegator;

import com.example.config.ChatModelFactory;
import com.example.worker.FlightWorkerService;
import com.example.worker.HotelWorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.langchain4j.model.chat.ChatLanguageModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DelegatorServiceTest {

    @Mock
    private ChatModelFactory chatModelFactory;
    
    @Mock
    private ChatLanguageModel chatModel;
    
    @Mock
    private FlightWorkerService flightWorkerService;
    
    @Mock
    private HotelWorkerService hotelWorkerService;

    private DelegatorService delegatorService;

    @BeforeEach
    void setUp() {
        when(chatModelFactory.createChatModel()).thenReturn(chatModel);
        when(chatModel.generate(anyString())).thenReturn(
            "Complete travel itinerary with coordinated flight and hotel bookings"
        );
        
        when(flightWorkerService.searchFlights(anyString(), anyString(), anyString(), anyString()))
            .thenReturn("Flight recommendation: Delta Airlines $780");
            
        when(hotelWorkerService.searchHotels(anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn("Hotel recommendation: Citadines Saint-Germain $320/night");
        
        delegatorService = new DelegatorService(chatModelFactory, flightWorkerService, hotelWorkerService);
    }

    @Test
    void testDelegateAndCoordinate() {
        // Given
        String plan = "Travel plan for Paris trip";
        String origin = "New York";
        String destination = "Paris";
        String departureDate = "2025-05-07";
        String returnDate = "2025-05-14";
        String budget = "8000";
        String preferences = "Anniversary trip";

        // When
        String result = delegatorService.delegateAndCoordinate(
            plan, origin, destination, departureDate, returnDate, budget, preferences
        );

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // Verify workers were called
        verify(flightWorkerService).searchFlights(origin, destination, departureDate, preferences);
        verify(hotelWorkerService).searchHotels(destination, departureDate, returnDate, budget, preferences);
    }
}