package com.wilterson;

import com.wilterson.dal.FlightPlanDataService;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainRunner implements CommandLineRunner {

    private final FlightPlanDataService flightPlanDataService;

    public MainRunner(FlightPlanDataService flightPlanDataService) {
        this.flightPlanDataService = flightPlanDataService;
    }

    @Override
    public void run(String... args) throws Exception {
//        flightPlanDataService.insertInitialFlightPlans();
//        System.out.println(flightPlanDataService.findInternationalCrossingFrance());
//        System.out.println(flightPlanDataService.findByFullTextSearch("Paris"));
        flightPlanDataService.incrementDepartureTime("684721043aaf71186734677d", LocalDateTime.now());
    }
}
