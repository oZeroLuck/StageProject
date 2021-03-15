package com.rental.dao;

import com.rental.entity.User;
import com.rental.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {

    public void saveCustomer(User theCustomer) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(theCustomer);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }

    }

    public List<User> getCustomers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    public User checkLogin(String email, String password) throws Exception {

        Transaction transaction = null;
        User user = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("FROM User U WHERE U.email = :email").setParameter("email", email).uniqueResult();

            //Testing
            //System.out.println(user.getEmail() + " " + user.getPassword() );


            if (user != null && user.getPassword().equals(password)) {
                //Testing
                //System.out.println("Trovato!");
                return user;
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction !=null)
                transaction.rollback();
            e.printStackTrace();
        }

        //Testing
        //System.out.println("Non trovato :(");
        return null;
       /* Session session = HibernateUtil.getSessionFactory().openSession();
        if (session != null) {
            try {
                User user = (User) session.get(User.class, email);
                if (password.equals(user.getPassword()))
                    return user;
            } catch (Exception e) {
                return null;
            }
        }
        return null; */
    }
}
