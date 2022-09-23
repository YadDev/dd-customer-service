package com.dev.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Addresses")
public class Address {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="id")
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="streetOne")
    private String streetOne;
    @Column(name="streetTwo")
    private String streetTwo;
    @Column(name="postalCode")
    private String postalCode;
    @Column(name="city")
    private String city;
    @Column(name="region")
    private String region;
    @Column(name="mobileNumber")
    private String mobileNumber;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetOne() {
        return streetOne;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String  region) {
        this.region = region;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
