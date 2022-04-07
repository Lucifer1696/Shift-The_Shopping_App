package com.example.shift.model;

import java.io.Serializable;

public class AddressesModel implements Serializable {

    private String address;

    public AddressesModel( String address) {
        this.address = address;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
