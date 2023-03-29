package model;

public class UserModel {
    public String email;
    public String password;

    public String name;
    public String phone_number;
    public String nid;
    public String role;
    public String from_account;
    public String to_account;

     public int amount;

    public UserModel(String email,String password){
        this.email = email;
        this.password = password;
    }

    public UserModel(String name, String email,String password,String phone_number,String nid,String role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.nid = nid;
        this.role = role;
    }

    


}
