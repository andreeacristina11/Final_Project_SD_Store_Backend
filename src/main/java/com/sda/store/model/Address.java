package com.sda.store.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String cuntry;
    private String city;
    private String street;
    private Long zipcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuntry() {
        return cuntry;
    }

    public void setCuntry(String cuntry) {
        this.cuntry = cuntry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getZipcode() {
        return zipcode;
    }

    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }
}
