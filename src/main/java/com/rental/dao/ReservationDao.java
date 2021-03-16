package com.rental.dao;

import java.util.List;

import com.rental.entity.User;
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

    public List<Reservation> getReservations(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation", Reservation.class).list();
        }
    }

    public Reservation getTheReservation(String reservationId) throws Exception {

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

            //Delete it
            session.delete(reservation);
            transaction.commit();
            System.out.println("Deleted");

        } catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

}
