package com.rental.entity;

import javax.persistence.*;

@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String type;

    @Column
    private String licencePlate;

    @Column
    private String model;

    @Column
    private String brand;

    @OneToOne(mappedBy = "theVehicle", cascade = CascadeType.ALL)
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }


    public Vehicle(String type, String licencePlate, String model, String brand) {
        this.type = type.toUpperCase();
        this.licencePlate = licencePlate.toUpperCase();
        this.model = model.toUpperCase();
        this.brand = brand.toUpperCase();
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
