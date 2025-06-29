package com.wilterson.dal;

import com.wilterson.domain.FlightPlan;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FlightPlanRepositoryDataService implements FlightPlanDataService {

    private static final int ONE_HOUR = 60;
    private final FlightPlanRepository repository;

    public FlightPlanRepositoryDataService(FlightPlanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insertInitialFlightPlans() {

        var parisToLondon = new FlightPlan(
                "Paris",
                "London",
                LocalDateTime.of(2023, 6, 1, 20, 15),
                90,
                List.of("France", "England"),
                true,
                AircraftFactory.buildBoeing737());

        repository.insert(parisToLondon);

        var parisToNice = new FlightPlan(
                "Paris",
                "Nice",
                LocalDateTime.of(2023, 7, 3, 9, 0),
                100,
                List.of("France"),
                false,
                AircraftFactory.buildEmbraerE175E2());

        var AmsterdamToParis = new FlightPlan(
                "Amsterdam",
                "Paris",
                LocalDateTime.of(2023, 8, 3, 6, 0),
                100,
                List.of("France", "Netherlands"),
                false,
                AircraftFactory.buildEmbraerE175E2());

        var istanbulToPhuket = new FlightPlan(
                "Istanbul, Turkey",
                "Phuket, Thailand",
                LocalDateTime.of(2023, 12, 15, 22, 50),
                600,
                List.of("Turkey", "Iran", "Pakistan", "Thailand"),
                true,
                AircraftFactory.buildAirbusA350());

        var istanbulToBucharest = new FlightPlan(
                "Istanbul, Turkey",
                "Bucharest, Romania",
                LocalDateTime.of(2023, 12, 15, 21, 30),
                600,
                List.of("Turkey", "Romania"),
                true,
                AircraftFactory.buildBoeing737());

        var berlinToNewYork = new FlightPlan(
                "Berlin, Germany",
                "New York, United States",
                LocalDateTime.of(2023, 9, 15, 0, 0),
                420,
                List.of("Germany", "England", "United States"),
                true,
                AircraftFactory.buildBoeing747());

        var viennaToBucharest = new FlightPlan(
                "Vienna, Austria",
                "Bucgarest, Romania",
                LocalDateTime.of(2023, 8, 1, 11, 30),
                75,
                List.of("Austria", "Hungary", "Romania"),
                true,
                AircraftFactory.buildBoeing737());

        var flightPlans = List.of(
                parisToNice,
                AmsterdamToParis,
                viennaToBucharest,
                berlinToNewYork,
                istanbulToPhuket,
                istanbulToBucharest);

        repository.insert(flightPlans);
    }

    @Override
    public FlightPlan findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<FlightPlan> findInternationalCrossingFrance() {
        return repository.findByIsInternationalCrossing("France");
    }

    @Override
    public List<FlightPlan> findFirstTwoFlightsWhichLastBetweenOneAndThreeHours() {
        return repository.findByFlightDurationBetween(ONE_HOUR, 3 * ONE_HOUR, PageRequest.of(0, 2));
    }

    @Override
    public List<FlightPlan> findBoeingFlightsAndOrderBySeatCapacity() {
        return repository.findByAirCraftModelContainsOrderByAirCraftSeatCapacity("Boeing");
    }

    @Override
    public void incrementDepartureTime(String id, LocalDateTime newDepartureTime) {

    }

    @Override
    public void changeDurationForFlightsInParis(int minutesToAdd) {

    }

    // UPDATE USING REPOSITORY

    @Override
    public void changeDepartureTimeById(String id, LocalDateTime newDepartureTime) {
        repository.findAndChangeDepartureTimeById(id, newDepartureTime);
    }

    @Override
    public void incrementDurationForFlightsInParis(int minutesToAdd) {
        repository.updateDurationWithDelayForDestination("Paris", minutesToAdd);
    }

    // DELETE

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllFromParis() {
        repository.deleteByDepartureCityContains("Paris");
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
