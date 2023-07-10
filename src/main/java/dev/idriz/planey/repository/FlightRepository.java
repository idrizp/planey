package dev.idriz.planey.repository;

import dev.idriz.planey.model.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The flight repository
 */
public interface FlightRepository extends CrudRepository<Flight, UUID> {

    /**
     * Find a flight by its departure airport
     *
     * @param departureAirport the departure airport
     * @return An optional flight.
     */
    Optional<Flight> findByDepartureAirport(String departureAirport);

    /**
     * Find a flight by its departure and arrival airport
     *
     * @param departureAirport the departure airport
     * @param arrivalAirport   the arrival airport
     * @return An optional flight.
     */
    Optional<Flight> findByDepartureAirportAndArrivalAirport(String departureAirport, String arrivalAirport);

    /**
     * Find a flight by its flight number
     *
     * @param flightNumber the flight number
     * @return An optional flight.
     */
    Optional<Flight> findByFlightNumber(String flightNumber);

}
