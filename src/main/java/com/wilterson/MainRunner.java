package com.wilterson;

import com.wilterson.dal.FlightPlanDataService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainRunner implements CommandLineRunner {

    private final FlightPlanDataService flightPlanTemplateDataService;
    private final FlightPlanDataService flightPlanRepositoryDataService;

    public MainRunner(@Qualifier("flightPlanTemplateDataService") FlightPlanDataService flightPlanTemplateDataService,
            @Qualifier("flightPlanRepositoryDataService") FlightPlanDataService flightPlanRepositoryDataService) {
        this.flightPlanTemplateDataService = flightPlanTemplateDataService;
        this.flightPlanRepositoryDataService = flightPlanRepositoryDataService;
    }

    @Override
    public void run(String... args) throws Exception {
//        flightPlanRepositoryDataService.insertInitialFlightPlans();
//        System.out.println(flightPlanDataService.findInternationalCrossingFrance());
//        System.out.println(flightPlanDataService.findByFullTextSearch("Paris"));
//        flightPlanTemplateDataService.incrementDepartureTime("684721043aaf71186734677d", LocalDateTime.now());
//        System.out.println(flightPlanRepositoryDataService.findById("684721043aaf71186734677c"));
//        System.out.println(flightPlanRepositoryDataService.findFirstTwoFlightsWhichLastBetweenOneAndThreeHours());
//        System.out.println(flightPlanRepositoryDataService.findBoeingFlightsAndOrderBySeatCapacity());
//        System.out.println(flightPlanRepositoryDataService.findInternationalCrossingFrance());
//        flightPlanRepositoryDataService.changeDepartureTimeById("684c880a4ca8f2e5541df9c4", LocalDateTime.now());
//        flightPlanRepositoryDataService.incrementDurationForFlightsInParis(25);
//        flightPlanRepositoryDataService.deleteAllFromParis();
//        flightPlanRepositoryDataService.deleteById("686171a6a550e836a6d5d9cf");
        flightPlanRepositoryDataService.deleteAll();
    }
}
