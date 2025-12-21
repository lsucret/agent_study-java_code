package com.example;

import com.example.model.TravelRequest;
import com.example.service.CWDTravelService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("test-runner")
public class CWDTestRunner implements CommandLineRunner {

    private final CWDTravelService cwdTravelService;

    public CWDTestRunner(CWDTravelService cwdTravelService) {
        this.cwdTravelService = cwdTravelService;
    }

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test-runner");
        SpringApplication.run(CWDTestRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== CWD Travel Planner Test Runner ===");
        
        TravelRequest request = new TravelRequest(
            "New York",
            "Paris", 
            "2025-05-07",
            "2025-05-14",
            8000,
            2,
            "Anniversary trip, moderate pace, prefer direct flights and central hotel with WiFi"
        );

        System.out.println("Processing travel request...");
        String result = cwdTravelService.planTravel(request);
        
        System.out.println("\n=== TRAVEL PLAN RESULT ===");
        System.out.println(result);
        System.out.println("=========================");
    }
}