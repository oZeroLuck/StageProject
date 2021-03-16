package com.rental.entity;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "reservation")
    private Vehicle theVehicle;

    @Column
    private String startDate;

    @Column
    private String duration;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private User theCustomer;

    public  Reservation() {

    }

    public Reservation(Vehicle theVehicle, String startDate, String duration, User theCustomer) {
        this.theVehicle = theVehicle;
        this.startDate = startDate;
        this.duration = duration;
        this.theCustomer = theCustomer;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Vehicle getTheVehicle() {
        return theVehicle;
    }

    public void setTheVehicle(Vehicle theVehicle) {
        this.theVehicle = theVehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getTheCustomer() {
        return theCustomer;
    }

    public void setTheCustomer(User theCustomer) {
        this.theCustomer = theCustomer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
