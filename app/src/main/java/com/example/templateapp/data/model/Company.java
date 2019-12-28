package com.example.templateapp.data.model;

public class Company {

    String company_uid;
    String company_name;
    String company_address;
    String company_email;
    String company_contact_number;
    String current_uid;

    public Company(){}


    public Company(String company_uid, String company_name, String company_address, String company_email, String company_contact_number, String current_uid) {
        this.company_uid = company_uid;
        this.company_name = company_name;
        this.company_address = company_address;
        this.company_email = company_email;
        this.company_contact_number = company_contact_number;
        this.current_uid = current_uid;
    }

    public String getCompanyUid() {
        return company_uid;
    }

    public void setCompanyUid(String company_uid) {
        this.company_uid = company_uid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getCompany_contact_number() {
        return company_contact_number;
    }

    public void setCompany_contact_number(String company_contact_number) {
        this.company_contact_number = company_contact_number;
    }

    public String getCurrent_uid() {
        return current_uid;
    }

    public void setCurrent_uid(String current_uid) {
        this.current_uid = current_uid;
    }
}
