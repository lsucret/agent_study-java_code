package com.example.coordinator;

import com.example.config.ChatModelFactory;
import com.example.model.TravelRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.langchain4j.model.chat.ChatLanguageModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceTest {

    @Mock
    private ChatModelFactory chatModelFactory;
    
    @Mock
    private ChatLanguageModel chatModel;

    private CoordinatorService coordinatorService;

    @BeforeEach
    void setUp() {
        when(chatModelFactory.createChatModel()).thenReturn(chatModel);
        when(chatModel.generate(anyString())).thenReturn(
            "Mock travel plan: Flight booking, Hotel booking, Activities planning"
        );
        
        coordinatorService = new CoordinatorService(chatModelFactory);
    }

    @Test
    void testPlanTravel() {
        // Given
        TravelRequest request = new TravelRequest(
            "New York", "Paris", "2025-05-07", "2025-05-14", 
            8000, 2, "Anniversary trip"
        );

        // When
        String result = coordinatorService.planTravel(request);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}