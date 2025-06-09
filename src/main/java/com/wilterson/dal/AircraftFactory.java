package com.wilterson.dal;

import com.wilterson.domain.Aircraft;
import com.wilterson.domain.WakeTurbulence;

public class AircraftFactory {

    public static Aircraft buildBoeing737() {
        return new Aircraft("Boeing 737", 200, WakeTurbulence.Light);
    }

    public static Aircraft buildBoeing747() {
        return new Aircraft("Boeing 747", 250, WakeTurbulence.Light);
    }

    public static Aircraft buildAirbusA350() {
        return new Aircraft("Airbus A350", 100, WakeTurbulence.Medium);
    }

    public static Aircraft buildEmbraerE175E2() {
        return new Aircraft("Embraer E175 E2", 80, WakeTurbulence.Medium);
    }
}
