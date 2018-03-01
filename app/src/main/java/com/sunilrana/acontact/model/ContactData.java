package com.sunilrana.acontact.model;

import java.util.ArrayList;

public class ContactData {

    private String id;
    private String name;
    private ArrayList<String> email;
    private ArrayList<String> phone;

    public ContactData(String id, String name, ArrayList<String> phone, ArrayList<String> email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }


}