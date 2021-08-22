package models;

import javax.persistence.*;

@Entity
@Table(name="FlightBookings")
public class FlightBookings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name="flight_id",referencedColumnName = "flight_id")
    private FlightDetails flight;

    @Column(name="flight_capacity")
    private Long capacity= Long.valueOf(200);

    @Column(name="flight_current_capacity")
    private Long currCapacity= Long.valueOf(200);


    public Long getCurrCapacity() {
        return currCapacity;
    }

    public void setCurrCapacity(Long currCapacity) {
        this.currCapacity = currCapacity;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public FlightDetails getFlight() {
        return flight;
    }

    public void setFlight(FlightDetails flight) {
        this.flight = flight;
    }
}
