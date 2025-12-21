package com.example.tool;

import dev.langchain4j.agent.tool.Tool;

public class TransportSearchTool {
    
    @Tool("""
    Find local transportation options between locations.
    
    Args:
        location: City name
        origin: Starting point (e.g., "Airport", "Hotel", "Eiffel Tower")
        destination: End point (e.g., "City Center", "Museum", "Restaurant")
    
    Returns:
        Json containing transportation options with type, cost, duration, pros, cons
    """)
    public String findTransportation(String location, String origin, String destination) {
        return """
            {
                "options": [
                    {
                        "type": "Metro",
                        "cost": 1.90,
                        "duration": "25 minutes",
                        "frequency": "Every 5 minutes",
                        "route": "Line 1 to Châtelet, then Line 4 to destination",
                        "pros": "Fast, avoids traffic",
                        "cons": "Can be crowded during peak hours"
                    },
                    {
                        "type": "Taxi",
                        "cost": 22.50,
                        "duration": "20 minutes",
                        "frequency": "On demand",
                        "route": "Direct",
                        "pros": "Door-to-door service, comfortable",
                        "cons": "More expensive, subject to traffic"
                    },
                    {
                        "type": "Bus",
                        "cost": 1.90,
                        "duration": "35 minutes",
                        "frequency": "Every 10 minutes",
                        "route": "Route 42 direct to destination",
                        "pros": "Scenic route, above ground",
                        "cons": "Slower than metro, subject to traffic"
                    }
                ],
                "passes": [
                    {
                        "name": "Day Pass",
                        "cost": 7.50,
                        "valid_for": "Unlimited travel for 24 hours",
                        "recommended_if": "Making more than 4 trips in a day"
                    }
                ]
            }
            """;
    }
}