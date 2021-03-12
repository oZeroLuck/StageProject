package com.rental.entity;
/*  Temporary removal to create a faster test
import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CarPark {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String carParkName;

    @OneToMany (mappedBy = "carPark")
    private List<Vehicle> vehicles;

    public CarPark(){

    }


    @Override
    public String toString() {
        return "CarPark{" +
                "id=" + id +
                ", carParkName='" + carParkName + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }

    public int id() {
        return id;
    }

    public void setCarParkIt(int carParkIt) {
        this.id = carParkIt;
    }

    public String getCarParkName() {
        return carParkName;
    }

    public void setCarParkName(String carParkName) {
        this.carParkName = carParkName;
    }

}*/
