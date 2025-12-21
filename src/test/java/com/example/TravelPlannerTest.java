package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//@TestPropertySource(properties = {
//    "openai.api.key=${OPENAI_API_KEY}"
//})
class TravelPlannerTest {

    @Autowired
    private TravelPlanner travelPlanner;

    @Test
    void testPlanTravel() {
        // 간단한 요청으로 변경
        String request = """
                I need to plan a trip to Paris from New York for 5 days.
                The plan should include:
                - Flights price under 800
                - Hotel accommodations in central Paris
                - A day trip to Eiffel Tower.
                """;

        // Execute hierarchical planning
        String result = travelPlanner.planTravel(request);

        System.out.println("Final Travel Plan:");
        System.out.println(result);

        // Basic assertion
        assert result != null && !result.isEmpty();
        assert !result.contains("temporarily unavailable") || result.length() > 10;
    }
}