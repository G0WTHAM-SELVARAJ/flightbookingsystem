package models;



import javax.persistence.*;

@Entity
@Table(name="UserBookingHistories")
public class UserBookingHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private UserProfiles user;

    @ManyToOne
    @JoinColumn(name="booking_id",referencedColumnName = "booking_id")
    private FlightBookings flightBooking;



    public static enum Booking{
        cancelled,
        booked,
        expired
    }

    @Column(name="booking_status")
     @Enumerated(EnumType.STRING)
    private Booking booking;


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public Long getHistoryId() {
        return historyId;
    }

    public UserProfiles getUser() {
        return user;
    }

    public void setUser(UserProfiles user) {
        this.user = user;
    }

    public FlightBookings getFlightBooking() {
        return flightBooking;
    }

    public void setFlightBooking(FlightBookings flightBooking) {
        this.flightBooking = flightBooking;
    }







}
