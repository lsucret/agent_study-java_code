package com.example.tool;

import dev.langchain4j.agent.tool.Tool;

public class FlightSearchTool {
    
    @Tool("""
    Search for available flights between cities.
    
    Args:
        origin: Departure city
        destination: Arrival city
        date: Travel date (YYYY-MM-DD)
    
    Returns:
        Json containing flight airline, price, departure, arrival, duration, departure_time, arrival_time
    """)
    public String searchFlights(String origin, String destination, String date) {
        return """
            {
                "flights": [
                    {
                        "airline": "Air France",
                        "price": 850,
                        "departure": "New York (JFK)",
                        "arrival": "Paris (CDG)",
                        "duration": "7h 30m",
                        "departure_time": "10:30 AM",
                        "arrival_time": "11:00 PM"
                    },
                    {
                        "airline": "Delta Airlines",
                        "price": 780,
                        "departure": "New York (JFK)",
                        "arrival": "Paris (CDG)",
                        "duration": "7h 45m",
                        "departure_time": "5:30 PM",
                        "arrival_time": "6:15 AM"
                    },
                    {
                        "airline": "United Airlines",
                        "price": 920,
                        "departure": "New York (EWR)",
                        "arrival": "Paris (CDG)",
                        "duration": "7h 55m",
                        "departure_time": "8:45 PM",
                        "arrival_time": "9:40 AM"
                    }
                ]
            }
            """;
    }
}