package com.wilterson.dal;

import com.wilterson.domain.FlightPlan;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface FlightPlanRepository extends MongoRepository<FlightPlan, String> {

    List<FlightPlan> findByFlightDurationBetween(int minDuration, int maxDuration, PageRequest pageRequest);

    List<FlightPlan> findByAirCraftModelContainsOrderByAirCraftSeatCapacity(String model);

    //    findInternationalCrossingFrance
    @Query("""
            {
                'isInternational': true,
                'crossedCountries': {
                    '$in': [?0]
                }
            }
            """)
    List<FlightPlan> findByIsInternationalCrossing(String country);

    @Update("""
            {
                '$set': {
                    'departureDateTime': ?1
                }
            }
            """)
    void findAndChangeDepartureTimeById(String id, LocalDateTime newDepartureTime);

    @Query("{'destination': {'$regex': ?0}}")
    @Update("{'$inc': {'flightDuration': ?1}}")
    void updateDurationWithDelayForDestination(String destination, int delay);

    int deleteByDepartureCityContains(String city);
}
