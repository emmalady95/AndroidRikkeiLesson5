package com.example.emmalady.rikkeiandroidlesson5.model;

/**
 * Created by Liz Nguyen on 21/10/2017.
 */

public class Contact {
    private int id;
    private String contactName;
    private int contactNumber;

    public Contact (int id, String contactName, int contactNumber){
        this.id = id;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }
    public Contact (String contactName, int contactNumber){
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }
}
