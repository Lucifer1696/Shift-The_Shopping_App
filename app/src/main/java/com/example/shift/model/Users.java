package com.example.shift.model;

import java.io.Serializable;

public class Users  implements Serializable {

    String profilepic, username, password, email, userid, phoneNumber;

    public Users(String profilepic, String username, String password, String email, String userid, String phoneNumber) {
        this.profilepic = profilepic;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userid = userid;
        this.phoneNumber = phoneNumber;
    }

    public Users(){

    }

    /// methods for signin and sign up
    public Users( String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }



    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


