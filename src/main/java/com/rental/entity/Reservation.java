package com.rental.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;


    @OneToOne
    @JoinColumn(name="vehicle_id", referencedColumnName = "id")
    private Vehicle theVehicle;

    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    private String approved;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private User theCustomer;

    public  Reservation() {

    }

    public Reservation(Vehicle theVehicle, Date startDate, Date endDate, User theCustomer) {
        this.theVehicle = theVehicle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = "pending";
        this.theCustomer = theCustomer;
    }

    public String getApproved() {return approved; }

    public void setApproved(String approved) {this.approved = approved; }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
