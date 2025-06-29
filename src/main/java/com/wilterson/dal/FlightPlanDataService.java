package com.wilterson.dal;

import com.wilterson.domain.FlightPlan;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightPlanDataService {

    void insertInitialFlightPlans();

    FlightPlan findById(String id);

    List<FlightPlan> findInternationalCrossingFrance();

    List<FlightPlan> findFirstTwoFlightsWhichLastBetweenOneAndThreeHours();

    List<FlightPlan> findBoeingFlightsAndOrderBySeatCapacity();

    // Update flight plan with given ID and modify the departure time with provided value
    void incrementDepartureTime(String id, LocalDateTime newDepartureTime);

    // Add delay in minutes for all the flights leaving from Paris
    void changeDurationForFlightsInParis(int minutesToAdd);

    void changeDepartureTimeById(String id, LocalDateTime newDepartureTime);

    void incrementDurationForFlightsInParis(int minutesToAdd);

    void deleteById(String id);

    void deleteAllFromParis();

    void deleteAll();
}
