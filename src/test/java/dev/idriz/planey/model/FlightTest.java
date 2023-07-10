package dev.idriz.planey.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    @Test
    void updateArrival_arrivalTimeAndDateUpdated() {
        Flight flight = new Flight();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        flight.updateArrival(now);

        assertEquals(flight.getArrivalTime(), now.toLocalTime());
        assertEquals(flight.getArrivalDate(), now.toLocalDate());
    }

    @Test
    void updateDeparture_departureTimeAndDateUpdated() {
        Flight flight = new Flight();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        flight.updateDeparture(now);

        assertEquals(flight.getDepartureTime(), now.toLocalTime());
        assertEquals(flight.getDepartureDate(), now.toLocalDate());
    }
}