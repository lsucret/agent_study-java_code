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
class HotelWorkerServiceTest {

    @Mock
    private ChatModelFactory chatModelFactory;
    
    @Mock
    private ChatLanguageModel chatModel;

    private HotelWorkerService hotelWorkerService;

    @BeforeEach
    void setUp() {
        when(chatModelFactory.createChatModel()).thenReturn(chatModel);
        when(chatModel.generate(anyString())).thenReturn(
            "Recommended hotel: Citadines Saint-Germain, $320/night, excellent location"
        );
        
        hotelWorkerService = new HotelWorkerService(chatModelFactory);
    }

    @Test
    void testSearchHotels() {
        // Given
        String location = "Paris";
        String checkIn = "2025-05-07";
        String checkOut = "2025-05-14";
        String budget = "400";
        String preferences = "Central location, WiFi required";

        // When
        String result = hotelWorkerService.searchHotels(location, checkIn, checkOut, budget, preferences);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("hotel") || result.contains("Hotel"));
    }
}