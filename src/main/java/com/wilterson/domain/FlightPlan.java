package com.wilterson.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FlightPlans")
public class FlightPlan {

    @Id
    private String id;
    @Field(name="destination")
    private String departureCity;
    @Field(name="departure")
    private String destinationCity;
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime departureDateTime;
    private int flightDuration;
    private List<String> crossedCountries;
    private boolean isInternational;
    private AirCraft airCraft;

    public FlightPlan(String id, String departureCity, String destinationCity, LocalDateTime departureDateTime, int flightDuration,
            List<String> crossedCountries,
            boolean isInternational, AirCraft airCraft) {
        this.id = id;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureDateTime = departureDateTime;
        this.flightDuration = flightDuration;
        this.crossedCountries = crossedCountries;
        this.isInternational = isInternational;
        this.airCraft = airCraft;
    }

    public String getId() {
        return id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public List<String> getCrossedCountries() {
        return crossedCountries;
    }

    public boolean isInternational() {
        return isInternational;
    }

    public AirCraft getAirCraft() {
        return airCraft;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public void setCrossedCountries(List<String> crossedCountries) {
        this.crossedCountries = crossedCountries;
    }

    public void setInternational(boolean international) {
        isInternational = international;
    }

    public void setAirCraft(AirCraft airCraft) {
        this.airCraft = airCraft;
    }

    @Override
    public String toString() {
        return "FlightPlan{" +
                "id='" + id + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", flightDuration=" + flightDuration +
                ", crossedCountries=" + crossedCountries +
                ", isInternational=" + isInternational +
                ", airCraft=" + airCraft +
                '}';
    }
}
