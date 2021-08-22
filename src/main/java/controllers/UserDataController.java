package controllers;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserBookingHistoriesDAO;
import dao.UserProfilesDAO;
import frameworks.ResponsePattern;
import models.UserBookingHistories;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.params.PathParam;

@Singleton
public class UserDataController {
    @Inject
    UserProfilesDAO upd;

    @Inject
    UserBookingHistoriesDAO ubhd;

    public Result getAllBookingHistory(){
        return Results.json().render(ubhd.getUserBookingHistories());
    }

    public Result getUserHistory(@PathParam("id")Long userId){
        return  Results.json().render(ubhd.getUserBookingHistoryById(userId));
    }

    public Result creditUserWallet(@Param("userName")String userName, @Param("walletAmount")Long walletAmount){
        boolean credited = upd.addCreditByName(userName,walletAmount);
        ResponsePattern rp = new ResponsePattern();
        if(credited){
            rp.status="ok";
            rp.statusCode=200;
            rp.message="Credit added successfully";
            rp.actionType="amount_credited";

        }
        else{
            rp.status="Not ok";
            rp.statusCode=402;
            rp.message="Credit Not added";
            rp.errorType="credit transaction failed";
            rp.actionType="amount_not_credited";
        }
        return Results.json().render(rp);
    }
    public Result debitUserWallet(@Param("userName")String userName, @Param("walletAmount")Long walletAmount){
        boolean debited = upd.removeCreditByName(userName,walletAmount);
        ResponsePattern rp = new ResponsePattern();
        if(debited){
            rp.status="ok";
            rp.statusCode=200;
            rp.message="Amount Debited successfully";
            rp.actionType="amount_debited";
        }
        else{
            rp.status="Not ok";
            rp.statusCode=402;
            rp.message="Not debited amount";
            rp.errorType="debit transaction failed";
            rp.actionType="amount_not_debited";
        }
        return Results.json().render(rp);
    }
}

