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

            // Can't seem to get rid of you :(
            if(commandId == null)
                commandId = "LIST";

            // Sorting out the actions
            switch (commandId) {
                case "LOAD_CARS":
                    loadVehicles(request, response);
                    break;

                case "LOAD":
                    loadReservation(request, response);
                    break;

                // Default to listing
                default:
                    listReservation(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    //Get methods

    private void listReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ReservationDao reservationDao = new ReservationDao();
        List<Reservation> reservations = reservationDao.getReservations(user.getUserId());
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer_homepage.jsp");
        request.setAttribute("reservation_list", reservations);
        dispatcher.forward(request, response);

    }

    private void loadVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicles = vehicleDao.getVehicles();
        request.setAttribute("vehicles_list", vehicles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp");
        dispatcher.forward(request, response);

    }

    private void loadReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //Get the reservation id
        String reservationId = request.getParameter("reservationId");

        //Getting the reservation
        ReservationDao reservationDao = new ReservationDao();
        Reservation theReservation = reservationDao.getTheReservation(reservationId);

        //Place the reservation in request attribute
        request.setAttribute("theReservation", theReservation);

        //Send to jsp page
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("update_reservation.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Getting the command from the request
            String commandId = request.getParameter("command");

            switch (commandId) {
                case "ADD":
                    addReservation(request, response);
                    break;

                case "DELETE":
                    deleteReservation(request, response);
                    break;

                case "UPDATE":
                    updateReservation(request, response);
                    break;

                default:
                    System.out.println("Something went wrong son");
                    break;
            }

            } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    // Post methods

    private void addReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //Get user from session
        HttpSession session = request.getSession();
        User currentUser =(User) session.getAttribute("user");

        //Get vehicle id
        String vehicleId = request.getParameter("selected");

        //Set date
        Date currentDate = new Date();
        String startDate = currentDate.toString();
        String duration = request.getParameter("duration");

        //Get vehicle from id
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle theVehicle = vehicleDao.getTheVehicle(vehicleId);


        Reservation theReservation = new Reservation(theVehicle, startDate, duration, currentUser);
        ReservationDao theReservationDao = new ReservationDao();

        theReservationDao.saveReservation(theReservation);

        response.sendRedirect("CarParkControllerServlet");

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

        response.sendRedirect("CarParkControllerServlet");

    }


    //TODO: Implement update function
    private void updateReservation(HttpServletRequest request, HttpServletResponse response) {

        //Read the reservation id
        String reservationId = request.getParameter("reservationId");

    }
}

/*





 */
