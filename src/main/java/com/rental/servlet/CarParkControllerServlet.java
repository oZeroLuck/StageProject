package com.rental.servlet;

import com.rental.dao.ReservationDao;
import com.rental.dao.VehicleDao;
import com.rental.entity.Reservation;
import com.rental.entity.User;
import com.rental.entity.Vehicle;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CarParkControllerServlet", value = "/CarParkControllerServlet")
public class CarParkControllerServlet extends HttpServlet {

    public CarParkControllerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Getting the command from the request
            String commandId = request.getParameter("command");

            // Temporary "exception" catch
            if (commandId == null)
                commandId = "LIST";

            // Sorting out the actions
            switch (commandId) {
                case "LIST":
                    listReservation(request, response);

                case "LOAD_CARS":
                    loadVehicles(request, response);

                case "ADD":
                    addReservation(request, response);

                case "DELETE":
                    deleteReservation(request, response);

           /*   TODO: Add these functions

                case "LOAD":
                    loadReservation(request, response);

                case "UPDATE":
                    updateReservation(request, response); */

                default:
                    listReservation(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    //TODO: Fix the listing to only list the logged customer's reservations
    private void listReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        /*HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");*/
        ReservationDao reservationDao = new ReservationDao();
        List<Reservation> reservations = reservationDao.getReservations();
        request.setAttribute("reservation_list", reservations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer_homepage.jsp");
        dispatcher.forward(request, response);

    }

    private void loadVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicles = vehicleDao.getVehicles();
        request.setAttribute("vehicles_list", vehicles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp");
        dispatcher.forward(request, response);

    }


    private void addReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //Get user from session
        HttpSession session = request.getSession();
        User currentUser =(User) session.getAttribute("user");

        //Get vehicle id
        String vehicleId = request.getParameter("vehicleId");

        //Set date
        Date currentDate = new Date();

        //Get vehicle from id
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle theVehicle = vehicleDao.getTheVehicle(vehicleId);

        String startDate = currentDate.toString();

        Reservation theReservation = new Reservation(theVehicle, startDate, currentUser);
        ReservationDao theReservationDao = new ReservationDao();

        theReservationDao.saveReservation(theReservation);

        listReservation(request, response);

    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        /*Getting the values
        Maybe future use?
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");*/
        String reservationId = request.getParameter("reservationId");

        //Creating the DAO
        ReservationDao reservationDao = new ReservationDao();

        reservationDao.delete(reservationId);

        listReservation(request, response);

    }


}

/*
    private void updateReservation(HttpServletRequest request, HttpServletResponse response) {
    }




    private void loadReservation(HttpServletRequest request, HttpServletResponse response) {
    } */
