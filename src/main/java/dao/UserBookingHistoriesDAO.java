package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.FlightBookings;
import models.UserBookingHistories;
import models.UserProfiles;
import ninja.jpa.UnitOfWork;
import org.h2.engine.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserBookingHistoriesDAO {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public List<UserBookingHistories> getUserBookingHistories(){
        List<UserBookingHistories> liu = null;
        try{
            EntityManager em = entityManagerProvider.get();
            liu = (List<UserBookingHistories>) em.createQuery("From UserBookingHistories ")
                    .getResultList();
            return liu;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return liu;
        }
    }

    @UnitOfWork
    public List<UserBookingHistories> getUserBookingHistoryById(Long userId){
        List<UserBookingHistories> ubh = null;
        try {
            EntityManager em = entityManagerProvider.get();
            ubh = (List<UserBookingHistories>) em.createQuery("From UserBookingHistories Where user.user_id=?1")
                    .setParameter(1,userId).getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return ubh;
        }
    }

    @Transactional
    public void insertHistory(UserProfiles up, FlightBookings fb){
        EntityManager em = null;
        try{
            em = entityManagerProvider.get();
            UserBookingHistories ubh = new UserBookingHistories();
            ubh.setBooking(UserBookingHistories.Booking.booked);
            ubh.setFlightBooking(fb);
            ubh.setUser(up);
            em.persist(ubh);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
