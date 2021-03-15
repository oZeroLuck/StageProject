package com.rental.dao;

import java.util.List;

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

}
