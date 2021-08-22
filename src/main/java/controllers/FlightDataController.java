package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.FlightBookingsDAO;
import dao.FlightDetailsDAO;
import dao.UserProfilesDAO;
import frameworks.RequestEntities.Confirmation;
import frameworks.ResponsePattern;
import models.FlightDetails;
import models.UserProfiles;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

import java.text.SimpleDateFormat;

@Singleton
public class FlightDataController {
    @Inject
    FlightDetailsDAO fdd;

    @Inject
    FlightBookingsDAO fbd;

    @Inject
    UserProfilesDAO upd;

    public Result confirmBooking(Confirmation cn){
        ResponsePattern rp = new ResponsePattern();
        FlightDetails fd = fdd.getFlightById(cn.getFlightId());
        if(upd.deductWalletAmount(cn.getUserId(),fd.getTicket_price())){
            rp = fbd.createBooking(upd.getUserById(cn.getUserId()),fd);
        }
        else{
            rp.status="Not ok";
            rp.statusCode=402;
            rp.errorType="insufficient_amount";
            rp.message="Wallet amount empty or not sufficient";
        }
        return Results.json().render(rp);
    }

    public Result cancelBooking(Confirmation cn){
        return Results.json().render(fbd.deleteIntoBooking(cn.getUserId(),cn.getFlightId()));
    }

    public Result searchByParams(@Param("filter")String filter,@Param("args")String args){
        System.out.println(filter+" "+args);
        if (filter.length()==0||args.length()==0) {
            return  Results.text().render("Invalid Query Params");
        }
        try{
            if(filter.equals("departure_time")) {
                return Results.json().render(fdd.getFlightByDepartureTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(args)));
            }
            if(filter.equals("arrival_time")){
                return Results.json().render(fdd.getFlightByArrivalTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(args)));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(filter.equals("source")){
            return Results.json().render(fdd.getFlightBySource(args));
        }

        else{
            return Results.json().render("Invalid filter or args");
        }

    }

    public Result addFlightDetail(FlightDetails fd){
        return Results.text().render(fdd.addFlight(fd));
    }

    public Result updateFlightDetail(FlightDetails fd, @Param("flightId")Long flightId){
        return Results.text().render(fdd.updateFlight(fd,flightId));
    }
    public Result retrieveAllFlightDetails(){
        return Results.json().render(fdd.getAllDetails());
    }

    public Result getCustomFlightDetail(@PathParam("flightId") Long flightId){
        return Results.json().render(fdd.getFlightById(flightId));
    }
    public Result deleteFlightDetail(@Param("flightId")Long flightId){
        return Results.text().render(fdd.deleteFlight(flightId));
    }


}
