package dev.idriz.planey.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "flights",
        indexes = {@Index(
                columnList = "flightNumber",
                name = "flightNumber_index"
        ), @Index(
                columnList = "arrivalAirport, departureAirport",
                name = "arrivalAirport_departureAirport_index"
        ), @Index(
                columnList = "departureDate, arrivalDate",
                name = "departureDate_arrivalDate_index"
        )}
)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID flightId;

    @Column
    private String flightNumber;

    @Column
    private String departureAirport;

    @Column
    private String arrivalAirport;

    @Column
    private LocalDate departureDate;

    @Column
    private LocalDate arrivalDate;

    @Column
    private LocalTime departureTime;

    @Column
    private LocalTime arrivalTime;

    @OneToOne
    private Flight nextFlight;

    @OneToMany
    private List<Ticket> tickets = new ArrayList<>();

    /**
     * Updates the arrival date and time of the flight
     *
     * @param arrivalDateTime the new arrival date and time
     */
    public void updateArrival(LocalDateTime arrivalDateTime) {
        this.arrivalDate = arrivalDateTime.toLocalDate();
        this.arrivalTime = arrivalDateTime.toLocalTime();
    }

    /**
     * Updates the departure date and time of the flight
     *
     * @param departureDateTime the new departure date and time
     */
    public void updateDeparture(LocalDateTime departureDateTime) {
        this.departureDate = departureDateTime.toLocalDate();
        this.departureTime = departureDateTime.toLocalTime();
    }

    public @Nullable Flight getNextFlight() {
        return nextFlight;
    }

    public void setNextFlight(Flight nextFlight) {
        this.nextFlight = nextFlight;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public UUID getFlightId() {
        return flightId;
    }

    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
