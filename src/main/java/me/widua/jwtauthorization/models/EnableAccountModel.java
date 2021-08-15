package me.widua.jwtauthorization.models;

public class EnableAccountModel {

    String username ;
    String specialCode ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }
}
