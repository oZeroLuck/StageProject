package com.rental.dao;

import com.rental.entity.Vehicle;
import com.rental.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VehicleDao {

    public List<Vehicle> getVehicles(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Vehicle ", Vehicle.class).list();
        }
    }

    public Vehicle getTheVehicle(String vehicleId) {

        if (vehicleId != null) {

            //To int
            int idToInt = Integer.parseInt(vehicleId);

            //Get session
            Session session = HibernateUtil.getSessionFactory().openSession();

            //Send requested vehicle
            return session.get(Vehicle.class, idToInt);

        } return null;

    }

    public void delete(String vehicleId) {

        // Setting session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Get vehicle by Id
            Vehicle vehicle = getTheVehicle(vehicleId);

            // Delete it
            session.delete(vehicle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction!=null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void saveVehicle(Vehicle vehicle) {
        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(vehicle);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateVehicle(Vehicle vehicle, String type, String brand, String model, String plate) {

        // Get session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Start transaction
            transaction = session.beginTransaction();

            // Set the info
            vehicle.setType(type);
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setLicencePlate(plate);

            // Update entity
            session.merge(vehicle);

            // Commit
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public List<Vehicle> available() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("from Vehicle as V where not exists (select id from Reservation as R where R.theVehicle = V)", Vehicle.class).list();
    }
}
