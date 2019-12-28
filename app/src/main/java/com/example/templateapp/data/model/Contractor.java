package com.example.templateapp.data.model;

public class Contractor {
    String uid;
    String email;
    String password;
    String name;
    String contactNumber;


    String confirmPassword;

    public Contractor(String uid, String email, String password, String name, String contactNumber, String confirmPassword) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.confirmPassword = confirmPassword;
    }

    public Contractor(){}

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }





}
