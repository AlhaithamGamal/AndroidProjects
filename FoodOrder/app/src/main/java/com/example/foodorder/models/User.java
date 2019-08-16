package com.example.foodorder.models;

public class User {
    private String Email;
    private String Name;
    private String Phone;
    private  String Password;
    private  String isStaff;

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getIsStaff() {
        return isStaff;
    }




    User(){


    }

    public User(String email, String name, String password) {
        Email = email;
        Name = name;
        Password = password;
        this.isStaff = "false";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
