package com.example.shazahassan.forecastapp.firebase;

/**
 * Created by Shaza Hassan on 18-Nov-18.
 */
public class ContactUsModel {
    String name, emai, message;

    public ContactUsModel(String name, String emai, String message) {
        this.name = name;
        this.emai = emai;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
