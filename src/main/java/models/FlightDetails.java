package models;



import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
public class FlightDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="flight_id")
    private Long flight_id;

    @Column(name="flight_source_location")
    private String flight_source_location;

    @Column(name="flight_destination_location")
    private String flight_destination_location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name="flight_departured_at")
    private Date flight_departured_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name="flight_arrived_at")
    private Date flight_arrived_at;

    @Column(name="ticket_price")
    private Long ticket_price;


    public Long getFlight_id() {
        return flight_id;
    }

    public String getFlight_source_location() {
        return flight_source_location;
    }

    public void setFlight_source_location(String flight_source_location) {
        this.flight_source_location = flight_source_location;
    }

    public String getFlight_destination_location() {
        return flight_destination_location;
    }

    public void setFlight_destination_location(String flight_destination_location) {
        this.flight_destination_location = flight_destination_location;
    }

    public Long getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(Long ticket_price) {
        this.ticket_price = ticket_price;
    }


    public FlightDetails(){

    }

    public Date getFlight_arrived_at() {
        return flight_arrived_at;
    }

    public void setFlight_arrived_at(Date flight_arrived_at) {
        this.flight_arrived_at = flight_arrived_at;
    }

    public Date getFlight_departured_at() {
        return flight_departured_at;
    }

    public void setFlight_departured_at(Date flight_departured_at) {
        this.flight_departured_at = flight_departured_at;
    }
}
