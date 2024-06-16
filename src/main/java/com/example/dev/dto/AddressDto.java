package com.example.dev.dto;

import com.example.dev.model.Countries;
import com.example.dev.model.provinces.Provinces;

public class AddressDto {
    private String addressLine1;
    private String addressLine2;
    private String zipcode;
    private String location;
    private Provinces province;
    private Countries country;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Provinces getProvince() {
        return province;
    }

    public void setProvince(Provinces provinces) {
        this.province = provinces;
    }

    public Countries getCountry() {
        return country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }
}