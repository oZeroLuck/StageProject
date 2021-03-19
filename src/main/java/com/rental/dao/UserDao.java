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
            return session.createQuery("FROM User as U WHERE U.isAdmin = false", User.class).list();

        }
    }

    public User checkLogin(String username, String password) throws Exception {

        Transaction transaction = null;
        User user = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("FROM User as U WHERE U.username = :username")
                    .setParameter("username", theUsername).uniqueResult();

            if (user != null && user.getPassword().equals(password))
                return user;

            transaction.commit();

        } catch (Exception e) {
            if (transaction !=null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;

    }

    public User getUser(String customerId) {

        int toInt = Integer.parseInt(customerId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.get(User.class, toInt);

    }

    public void deleteCustomer(String customerId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User customer = getUser(customerId);

            session.delete(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction!=null)
                transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateCustomer(User customer, String firstName, String lastName, String email, String username, String password) {

        // Get session and transaction
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try  {
            // Start transaction
            transaction = session.beginTransaction();

            // Set the info
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setUsername(username);
            customer.setPassword(password);

            // Update the entity
            session.merge(customer);

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