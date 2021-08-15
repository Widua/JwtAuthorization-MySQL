package me.widua.jwtauthorization.models;

public class UserChangePasswordModel {

    private String newPassword;
    private String repeatedNewPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatedNewPassword() {
        return repeatedNewPassword;
    }

    public void setRepeatedNewPassword(String repeatedNewPassword) {
        this.repeatedNewPassword = repeatedNewPassword;
    }

    public UserChangePasswordModel(String newPassword, String repeatedNewPassword) {
        this.newPassword = newPassword;
        this.repeatedNewPassword = repeatedNewPassword;
    }
}
