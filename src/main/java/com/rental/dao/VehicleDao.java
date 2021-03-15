package com.rental.dao;

import com.rental.entity.Vehicle;
import com.rental.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class VehicleDao {

    public List<Vehicle> getVehicles(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Vehicle ", Vehicle.class).list();
        }
    }

    public Vehicle getTheVehicle(String vehicleId) throws Exception {

        //To int
        int idToInt = Integer.parseInt(vehicleId);

        //Get session
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Send requested vehicle
        return session.get(Vehicle.class, idToInt);

    }

}
