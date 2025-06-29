package com.wilterson.dal;

import com.wilterson.domain.FlightPlan;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class FlightPlanTemplateDataService implements FlightPlanDataService {

    private MongoOperations mongoOperations;

    public FlightPlanTemplateDataService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void insertInitialFlightPlans() {

        var parisToNice = new FlightPlan(
                "Paris",
                "Nice",
                LocalDateTime.of(2023, 7, 3, 9, 0),
                100,
                List.of("France"),
                true,
                AircraftFactory.buildEmbraerE175E2());

        var AmsterdamToParis = new FlightPlan(
                "Amsterdam",
                "Paris",
                LocalDateTime.of(2023, 8, 3, 6, 0),
                100,
                List.of("France", "Netherlands"),
                false,
                AircraftFactory.buildEmbraerE175E2());

        var parisToLondon = new FlightPlan(
                "Paris",
                "London",
                LocalDateTime.of(2023, 6, 1, 20, 15),
                90,
                List.of("France", "England"),
                true,
                AircraftFactory.buildBoeing737());

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
                parisToLondon,
                viennaToBucharest,
                berlinToNewYork,
                istanbulToPhuket,
                istanbulToBucharest);

        this.mongoOperations.insert(flightPlans, FlightPlan.class);
    }

    @Override
    public FlightPlan findById(String id) {
        return mongoOperations.findById(id, FlightPlan.class);
    }

    @Override
    public List<FlightPlan> findInternationalCrossingFrance() {
        Criteria isInternational = Criteria.where("isInternational").is(true);
        Criteria crossingFrance = Criteria.where("crossedCountries").in("France");
        Criteria criteria = new Criteria().andOperator(isInternational, crossingFrance);
        Query query = new Query(criteria);
        return mongoOperations.find(query, FlightPlan.class);
    }

    @Override
    public List<FlightPlan> findFirstTwoFlightsWhichLastBetweenOneAndThreeHours() {
        Criteria flightDuration = Criteria.where("flightDuration").gte(60).lte(180);
        Query query = new Query(flightDuration).with(PageRequest.of(0, 2));
        return mongoOperations.find(query, FlightPlan.class);
    }

    @Override
    public List<FlightPlan> findBoeingFlightsAndOrderBySeatCapacity() {
        Criteria aircraftModel = Criteria.where("aircraft.model").regex("Boeing");
        Query query = new Query(aircraftModel).with(Sort.by("aircraft.seatCapacity").descending());
        return mongoOperations.find(query, FlightPlan.class);
    }

    public List<FlightPlan> findByFullTextSearch(String value) {
        TextCriteria matching = TextCriteria.forDefaultLanguage().matching(value);
        TextQuery textQuery = TextQuery.queryText(matching).sortByScore();
        return mongoOperations.find(textQuery, FlightPlan.class);
    }

    // Update flight plan with given ID and modify the departure time with provided value
    @Override
    public void incrementDepartureTime(String id, LocalDateTime newDepartureTime) {

        // this approach performs a full-scan in the DB, so it's not optimal
//        FlightPlan existingFlightPlan = this.findById(id);
//        existingFlightPlan.setDepartureDateTime(newDepartureTime);
//        mongoOperations.save(existingFlightPlan);

        // this is a better approach
        Query query = new Query(Criteria.where("id").is(id));
        Update departureDateTimeUpdate = new Update().set("departureDateTime", newDepartureTime);
        mongoOperations.updateFirst(query, departureDateTimeUpdate, FlightPlan.class);
    }

    // Add delay in minutes for all the flights leaving from Paris
    @Override
    public void changeDurationForFlightsInParis(int minutesToAdd) {
        Query query = new Query(Criteria.where("departure").regex("Paris"));
        Update flightDurationUpdate = new Update().inc("flightDuration", minutesToAdd);
        mongoOperations.updateMulti(query, flightDurationUpdate, FlightPlan.class);
    }

    // UPDATE USING REPOSITORY

    @Override
    public void changeDepartureTimeById(String id, LocalDateTime newDepartureTime) {

    }

    @Override
    public void incrementDurationForFlightsInParis(int minutesToAdd) {

    }

    // DELETE

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteAllFromParis() {

    }

    @Override
    public void deleteAll() {

    }
}
