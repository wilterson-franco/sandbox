package com.wilterson.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "FlightPlans")
public class FlightPlan {

    @Id
    private String id;
    @TextIndexed(weight = 2)
    @Field(name = "departure")
    private String departureCity;
    @TextIndexed
    @Field(name = "destination")
    private String destinationCity;
    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDateTime departureDateTime;
    private int flightDuration;
    @TextIndexed
    private List<String> crossedCountries;
    private boolean isInternational;
    private Aircraft airCraft;

    public FlightPlan(String departureCity, String destinationCity, LocalDateTime departureDateTime, int flightDuration, List<String> crossedCountries,
            boolean isInternational, Aircraft airCraft) {
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

    public Aircraft getAirCraft() {
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

    public void setAirCraft(Aircraft airCraft) {
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
