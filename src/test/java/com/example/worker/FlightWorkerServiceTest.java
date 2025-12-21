package com.example.worker;

import com.example.config.ChatModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.langchain4j.model.chat.ChatLanguageModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightWorkerServiceTest {

    @Mock
    private ChatModelFactory chatModelFactory;
    
    @Mock
    private ChatLanguageModel chatModel;

    private FlightWorkerService flightWorkerService;

    @BeforeEach
    void setUp() {
        when(chatModelFactory.createChatModel()).thenReturn(chatModel);
        when(chatModel.generate(anyString())).thenReturn(
            "Recommended flight: Delta Airlines, $780, direct flight"
        );
        
        flightWorkerService = new FlightWorkerService(chatModelFactory);
    }

    @Test
    void testSearchFlights() {
        // Given
        String origin = "New York";
        String destination = "Paris";
        String date = "2025-05-07";
        String preferences = "Direct flights preferred";

        // When
        String result = flightWorkerService.searchFlights(origin, destination, date, preferences);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("flight") || result.contains("Flight"));
    }
}