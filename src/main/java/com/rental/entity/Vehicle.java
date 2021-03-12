package com.rental.entity;

import javax.persistence.*;

@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String type;

    @Column
    private String licencePlate;

    @Column
    private String model;

    @Column
    private String manufacturer;

    public Vehicle(String type, String licencePlate, String model, String manufacturer) {
        this.type = type;
        this.licencePlate = licencePlate;
        this.model = model;
        this.manufacturer = manufacturer;
    }

    public Vehicle() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}