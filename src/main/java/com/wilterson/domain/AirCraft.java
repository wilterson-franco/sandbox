package com.wilterson.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "AirCrafts")
public class AirCraft {

    private String model;
    @Field(name = "capacity")
    private int seatCapacity;
    private WakeTurbulence wakeTurbulence;

    public AirCraft(String model, int seatCapacity, WakeTurbulence wakeTurbulence) {
        this.model = model;
        this.seatCapacity = seatCapacity;
        this.wakeTurbulence = wakeTurbulence;
    }

    public String getModel() {
        return model;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public WakeTurbulence getWakeTurbulence() {
        return wakeTurbulence;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public void setWakeTurbulence(WakeTurbulence wakeTurbulence) {
        this.wakeTurbulence = wakeTurbulence;
    }

    @Override
    public String toString() {
        return "AirCraft{" +
                "model='" + model + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", wakeTurbulence=" + wakeTurbulence +
                '}';
    }
}
