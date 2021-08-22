package models;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="UserProfiles")
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    Long user_id;

    @Column(name="user_name")
    private String user_name;

    @Column(name="user_password")
    private String user_password;

    @Column(name="user_email")
    private String user_email;



    enum Role
    {
        admin, customer
    }

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private Role user_role;

    @Column(name = "wallet_amount")
    private Long wallet_amount;


    public Long getUserWalletAmount(){
        return this.wallet_amount;
    }

    public void setUserWalletAmount(Long amount){
        this.wallet_amount = amount;
    }

    public Long getUserId(){
        return this.user_id;
    }
    public String getUserName(){
        return this.user_name;
    }
    public void setUserName(String name){
        this.user_name=name;
    }
    public void setUserPassword(String password){
        this.user_password=password;
    }
    public String getUserEmail(){
        return this.user_email;
    }
    public void setUserEmail(String email){
        this.user_email = email;
    }
    public Role getUserRole(){
        return this.user_role;
    }
    public void setUserRole(Role role){
        this.user_role = role;
    }
}