package controllers;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.UserProfilesDAO;
import frameworks.RequestEntities.UserLogin;
import frameworks.ResponsePattern;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;

@Singleton
@FilterWith(CORSFilter.class)
public class AuthController {
    @Inject
    UserProfilesDAO upd;


    public Result authVerify(UserLogin user){

        String name=null;
        String password=null;
        String email=null;
        try{
            name = user.getUserName();
            password = user.getUserPassword();
            email = user.getUserEmail();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        ResponsePattern rs = new ResponsePattern();
        Boolean nameValid = (name==null||name=="")?false:true;
        Boolean emailValid = (email==null||email=="")?false:true;
        Boolean authVerifyName = (nameValid&&upd.getUserAuthByName(name,password))?true:false;
        Boolean authVerifyEmail =(emailValid&&upd.getUserAuthByEmail(email,password))?true:false;
        System.out.println(email+" "+ name+" "+password);
        if(authVerifyEmail||authVerifyName){
            rs.status="ok";
            rs.statusCode=200;
            rs.message="success";
            rs.actionType="user_authenticated";
        }
        else{
            rs.status="not ok";
            rs.statusCode=402;
            rs.message="failed";
            rs.actionType="user_authentication_failed";
        }
        return Results.json().render(rs);
    }
}

