package com.example.tool;

import dev.langchain4j.agent.tool.Tool;

public class ActivitySearchTool {
    
    @Tool("""
            Search for available flights between cities.
               \s
                Args:
                    origin: Departure city
                    destination: Arrival city
                    date: Travel date (YYYY-MM-DD)
               \s
                Returns:
                    Dictionary containing flight options and prices
                    """)
    public String findActivities(String location, String date, String preferences) {
        // Python의 find_activities_lg 참고
        return """
            {
                "activities": [
                    {
                        "name": "Eiffel Tower Skip-the-Line",
                        "description": "Priority access to the Eiffel Tower with guided tour of 1st and 2nd floors",
                        "price": 65,
                        "duration": "2 hours",
                        "start_time": "10:00 AM",
                        "meeting_point": "Eiffel Tower South Entrance"
                    },
                    {
                        "name": "Louvre Museum Guided Tour",
                        "description": "Expert-guided tour of the Louvre's masterpieces including Mona Lisa",
                        "price": 85,
                        "duration": "3 hours",
                        "start_time": "2:00 PM",
                        "meeting_point": "Louvre Pyramid"
                    },
                    {
                        "name": "Seine River Dinner Cruise",
                        "description": "Evening cruise along the Seine with 3-course French dinner and wine",
                        "price": 120,
                        "duration": "2.5 hours",
                        "start_time": "7:30 PM",
                        "meeting_point": "Port de la Bourdonnais"
                    }
                ]
            }
            """;
    }
}