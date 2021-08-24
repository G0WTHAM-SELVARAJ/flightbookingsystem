package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.sun.org.apache.xpath.internal.operations.Bool;
import frameworks.ResponsePattern;
import models.FlightBookings;
import models.FlightDetails;
import models.UserBookingHistories;
import models.UserProfiles;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;

public class FlightBookingsDAO {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @Inject
    FlightDetailsDAO fdd;

    @Inject
    UserBookingHistoriesDAO ubh;


    @UnitOfWork
    public FlightBookings getFlightBookingById(Long FlightId){
        EntityManager em = null;
        FlightBookings fb = null;
        try{
            em = entityManagerProvider.get();
            fb = (FlightBookings) em.find(FlightBookings.class,FlightId);
        }
        catch(Exception e){
            System.out.println("FlightBookingDAO getFlightBookingById");
            e.printStackTrace();
        }
        finally {
            return fb;
        }
    }


    @Transactional
    public ResponsePattern createBooking(UserProfiles up, FlightDetails fd){
        ResponsePattern rp = new ResponsePattern();
        EntityManager em = null;
        try{
            em = entityManagerProvider.get();
            long count = (Long) em.createQuery("SELECT COUNT(*) FROM FlightBookings where flight.flight_id=?1")
                    .setParameter(1,fd.getFlight_id()).getSingleResult();
            boolean n = count!=0;
            System.out.println(count);
            if(!n){
                FlightBookings fb= new FlightBookings();
                fb.setFlight(fd);
                fb.setCurrCapacity(fb.getCurrCapacity()-1);
                em.persist(fb);
                ubh.insertHistory(up,fb);

            }
            else{
                FlightBookings fb = (FlightBookings) em.createQuery("From FlightBookings  where flight.flight_id= ?1")
                        .setParameter(1,fd.getFlight_id()).getSingleResult();
                fb.setCurrCapacity(fb.getCurrCapacity()-1);
                em.merge(fb);
                System.out.println(fb.getBookingId());
                ubh.insertHistory(up,fb);
            }
            rp.status="ok";
            rp.errorType="none";
            rp.actionType="flight_booked";
            rp.message="flight Booking successful";
        }
        catch (Exception e){
            System.out.println("FlightBookingsDAO createBooking");
            e.printStackTrace();
        }
        finally {
            return rp;
        }
    }




    @Transactional
    public ResponsePattern deleteIntoBooking(Long userId, Long flightID){
        EntityManager em = null;
        ResponsePattern rp = new ResponsePattern();
        try{
            em=entityManagerProvider.get();
            Long booking_id = (Long) em.createQuery("SELECT bookingId FROM FlightBookings " +
                    "where flight.flight_id = ?1").setParameter(1,flightID).getSingleResult();

            em.createQuery("UPDATE FlightBookings SET currCapacity = currCapacity -1 " +
                    "where flight.flight_id= ?1").setParameter(1,flightID).executeUpdate();

            UserBookingHistories ubh = (UserBookingHistories ) em.createQuery("FROM UserBookingHistories " +
                            "WHERE flightBooking.bookingId= ?1 AND user.user_id= ?2")
                    .setParameter(1,booking_id).setParameter(2,userId).getSingleResult();

            ubh.setBooking(UserBookingHistories.Booking.cancelled);
            em.merge(ubh);
            rp.status="ok";
            rp.errorType="none";
            rp.actionType="flight_booking_cancelled";
            rp.message="flight Booking cancelled successful";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return rp;
        }
    }
}
