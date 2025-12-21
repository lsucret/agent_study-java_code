package com.example.tool;

import dev.langchain4j.agent.tool.Tool;

public class HotelSearchTool {
    
    @Tool( """
    Search for available hotels in a location.
    
    Args:
        location: City name
        check_in: Check-in date (YYYY-MM-DD)
        check_out: Check-out date (YYYY-MM-DD)
    
    Returns:
        Json containing hotel name, price, check_in_date, check_out_date, rating, location, amenities
    """)
    public String findHotels(String location, String checkIn, String checkOut) {
        // Python의 find_hotels_lg 참고
        return """
            {
                "hotels": [
                    {
                        "name": "Paris Marriott Champs Elysees",
                        "price": 450,
                        "check_in_date": "%s",
                        "check_out_date": "%s",
                        "rating": 4.5,
                        "location": "Central Paris",
                        "amenities": ["Spa", "Restaurant", "Room Service"]
                    },
                    {
                        "name": "Citadines Saint-Germain-des-Prés",
                        "price": 280,
                        "check_in_date": "%s",
                        "check_out_date": "%s",
                        "rating": 4.2,
                        "location": "Saint-Germain",
                        "amenities": ["Kitchenette", "Laundry", "Wifi"]
                    },
                    {
                        "name": "Ibis Paris Eiffel Tower",
                        "price": 180,
                        "check_in_date": "%s",
                        "check_out_date": "%s",
                        "rating": 4.0,
                        "location": "Near Eiffel Tower",
                        "amenities": ["Restaurant", "Bar", "Wifi"]
                    }
                ]
            }
            """.formatted(checkIn, checkOut, checkIn, checkOut, checkIn, checkOut);
    }
}