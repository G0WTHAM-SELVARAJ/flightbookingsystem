package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.FlightDetails;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public class FlightDetailsDAO {

    @Inject
    Provider<EntityManager> entityManagerProvider;


    @Transactional
    public String updateFlight(FlightDetails fd,Long flightID){
        EntityManager em = null;
        try{
            em = entityManagerProvider.get();
            System.out.println(fd.getFlight_id());
            System.out.println(flightID);
            FlightDetails fdUpdate = em.find(FlightDetails.class,flightID);
            if(fd.getTicket_price()!=null){
                fdUpdate.setTicket_price(fd.getTicket_price());
            }
            if(fd.getFlight_source_location()!=null){
                fdUpdate.setFlight_source_location(fd.getFlight_source_location());
            }
            if(fd.getFlight_destination_location()!=null){
                fdUpdate.setFlight_destination_location((fd.getFlight_destination_location()));
            }
            if(fd.getFlight_departured_at()!=null){
                fdUpdate.setFlight_departured_at(fd.getFlight_departured_at());
            }
            if(fd.getFlight_arrived_at()!=null){
                fdUpdate.setFlight_arrived_at(fd.getFlight_arrived_at());
            }
            em.merge(fdUpdate);
        }
        catch(NullPointerException e){
            return "Flight Entity Does not exist";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return "Updated entity successfully";
        }
    }

    @Transactional
    public String deleteFlight(Long flightId){
        EntityManager em = null;
        try{
            em = entityManagerProvider.get();
            FlightDetails fd = em.find(FlightDetails.class,flightId);
            em.remove(fd);
        }
        catch(Exception e){
            e.printStackTrace();
            return "Internal Server Error";
        }
        finally {
            return "Entity deleted successfully";
        }
    }

    @Transactional
    public String addFlight(FlightDetails fd){
        EntityManager em = null;
        try{
            em = entityManagerProvider.get();
            em.persist(fd);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return "Inserted entity successfully";
        }
    }

    @UnitOfWork
    public List<FlightDetails> getFlightByArrivalTime(Date arrival){
        EntityManager em  = null;
        List<FlightDetails> fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (List<FlightDetails>) em.createQuery("From FlightDetails  WHERE flight_arrived_at>=?1").setParameter(1,arrival).getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return fd;
        }
    }


    @UnitOfWork
    public List<FlightDetails> getFlightByDepartureTime(Date departure){
        EntityManager em  = null;
        List<FlightDetails> fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (List<FlightDetails>) em.createQuery("From FlightDetails  WHERE  flight_departured_at>=?1").setParameter(1,departure).getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return fd;
        }
    }

    @UnitOfWork
    public List<FlightDetails> getFlightBySource(String source_location){
        EntityManager em = null;
        List<FlightDetails> fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (List<FlightDetails>) em.createQuery("FROM FlightDetails WHERE flight_source_location LIKE ?1")
                    .setParameter(1,source_location+"%").getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return fd;
        }
    }

    @UnitOfWork
    public List<FlightDetails> getFlightByDestination(String destination_location){
        EntityManager em = null;
        List<FlightDetails> fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (List<FlightDetails>) em.createQuery("FROM FlightDetails WHERE flight_destination_location LIKE ?1")
                    .setParameter(1,destination_location+"%").getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return fd;
        }
    }


    @UnitOfWork
    public FlightDetails getFlightById(Long flightId){
        EntityManager em = null;
        FlightDetails fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (FlightDetails)em.find(FlightDetails.class,flightId);
        }
        catch(Exception e){
            System.out.println("FlightDetailsDAO getFlightById");
            e.printStackTrace();
        }
        finally {
            return fd;
        }
    }

    @UnitOfWork
    public List<FlightDetails> getAllDetails(){
        EntityManager em = null;
        List<FlightDetails> fd = null;
        try{
            em = entityManagerProvider.get();
            fd = (List<FlightDetails>) em.createQuery("From FlightDetails ").getResultList();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            return fd;
        }
    }
}
