package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.sun.org.apache.xpath.internal.operations.Bool;
import models.UserProfiles;
import ninja.jpa.UnitOfWork;
import ninja.params.PathParam;
import org.h2.engine.User;

import javax.persistence.EntityManager;


public class UserProfilesDAO {
    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public UserProfiles getUserById(Long userId){
        EntityManager em = null;
        UserProfiles up = null;
        try{
            em=entityManagerProvider.get();
            up = (UserProfiles) em.find(UserProfiles.class,userId);
        }
        catch(Exception e){
            System.out.println("UserProfiles getUserById");
            e.printStackTrace();
        }
        finally{
            return up;
        }

    }

    @UnitOfWork
    public Boolean getUserAuthByEmail(String userEmail, String userPassword ){
        EntityManager em = null;
        UserProfiles usersQuery = null;
        try{
            em = entityManagerProvider.get();
            usersQuery = (UserProfiles) em.createQuery("FROM UserProfiles WHERE user_email= ?1 AND user_password= ?2").setParameter(1,userEmail).setParameter(2,userPassword).getSingleResult();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            if(usersQuery==null){
                return false;
            }
            return true;
        }
    }

    @UnitOfWork
    public Boolean getUserAuthByName(String userName, String userPassword ){
        EntityManager em = null;
        UserProfiles usersQuery = null;
        try{
            em = entityManagerProvider.get();
            usersQuery = (UserProfiles) em.createQuery("FROM UserProfiles WHERE user_name= ?1 AND user_password= ?2").setParameter(1,userName).setParameter(2,userPassword).getSingleResult();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            if(usersQuery==null){
                return false;
            }
            return true;
        }
    }

    @Transactional
    public Boolean deductWalletAmount(Long userId,Long deductAmount){
       boolean updated = false;
        try{
            EntityManager em = entityManagerProvider.get();
            UserProfiles user = (UserProfiles) em.createQuery("from UserProfiles where user_id= ?1 ")
                    .setParameter(1,userId).getSingleResult();
            long currWallet = user.getUserWalletAmount();
            if(currWallet>=deductAmount){
                currWallet-=deductAmount;
                user.setUserWalletAmount(currWallet);
                updated=true;
            }
            else{
                return false;
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            return updated;
        }
    }

    @Transactional
    public Boolean addCreditByName(String userName,Long walletAmount){
        try{
            EntityManager em = entityManagerProvider.get();
            UserProfiles user = (UserProfiles) em.createQuery("from UserProfiles where user_name= ?1 ").setParameter(1,userName).getSingleResult();
            Long curr_amount = user.getUserWalletAmount();
            curr_amount+=walletAmount;
            user.setUserWalletAmount(curr_amount);
            em.merge(user);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    public Boolean removeCreditByName(String userName,Long walletAmount){
        try{
            EntityManager em = entityManagerProvider.get();
            UserProfiles user = (UserProfiles) em.createQuery("from UserProfiles where user_name= ?1 ").setParameter(1,userName).getSingleResult();
            Long curr_amount = user.getUserWalletAmount();
            curr_amount-=walletAmount;
            user.setUserWalletAmount(curr_amount);
            em.merge(user);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }



}
