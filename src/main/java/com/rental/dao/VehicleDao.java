package com.rental.dao;

import com.rental.entity.Vehicle;
import com.rental.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class VehicleDao {

    public List<Vehicle> getVehicle(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Vehicle ", Vehicle.class).list();
        }
    }

}
