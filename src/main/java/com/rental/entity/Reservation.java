package com.rental.entity;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle theVehicle;

    @Column
    private String startDate;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private User theCustomer;

    public  Reservation() {

    }

    public Reservation(Vehicle theVehicle, String startDate, User theCustomer) {
        this.theVehicle = theVehicle;
        this.startDate = startDate;
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

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + id + '\'' +
                ", theVehicle='" + theVehicle + '\'' +
                ", theCustimer='" + theCustomer + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
