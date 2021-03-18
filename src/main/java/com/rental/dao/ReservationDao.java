package com.rental.dao;

import java.util.List;

import com.rental.entity.Vehicle;
import com.rental.util.HibernateUtil;
import com.rental.entity.Reservation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ReservationDao {

    public void saveReservation(Reservation theReservation){

        Transaction transaction = null;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(theReservation);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Reservation> getUserReservations(int userId){
        System.out.println("userId :" + userId);
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation as R where R.theCustomer.id = "
                    + userId, Reservation.class).list();
        }
    }

    public Reservation getTheReservation(String reservationId){

        //Converting Id from String to Int
        int toInt = Integer.parseInt(reservationId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.get(Reservation.class, toInt);

    }

    public void delete(String reservationId) {

        //Setting session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction =null;

        try {
            transaction = session.beginTransaction();

            //Getting the reservation by Id
            Reservation reservation = getTheReservation(reservationId);

            // Getting the vehicle by Id
            Vehicle vehicle = reservation.getTheVehicle();
            VehicleDao vehicleDao = new VehicleDao();

            vehicleDao.updateVehicle(vehicle, false);

            //Delete it
            session.delete(reservation);
            transaction.commit();
            //Testing
            //System.out.println("Deleted");

        } catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void updateReservation(Reservation updatedReservation, String newStartDate, String newEndDate) {

        // Get session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try  {
            // Start transaction
            transaction = session.beginTransaction();

            // Update the info
            updatedReservation.setStartDate(newStartDate);
            //updatedReservation.setEndDate(1);

            session.update(updatedReservation);

            // Commit
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            //Close connection
            session.close();
        }

    }

    public void approveReservation(Reservation reservation ,String verdict) {

        // Get session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try  {
            // Start transaction
            transaction = session.beginTransaction();

            // Update the info
            reservation.setApproved(verdict);
            session.merge(reservation);

            // Commit
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            //Close connection
            session.close();
        }

    }

}
