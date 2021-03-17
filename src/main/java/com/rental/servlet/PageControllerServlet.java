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
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "PageControllerServlet", value = "/PageControllerServlet")
public class PageControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandId = request.getParameter("command");

        if (commandId == null)
            commandId = "";

        try {
            switch (commandId) {
                case "CAR_PARK":
                    carPark(request, response);
                    break;

                default:
                    errorMessage(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void carPark(HttpServletRequest request, HttpServletResponse response) throws Exception{

        loadVehicles(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_park.jsp");
        dispatcher.forward(request, response);

    }
/* Preparing to refactor
    private void listReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ReservationDao reservationDao = new ReservationDao();

        // Branch between admin and customer types
        if (user.isAdmin()) {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            List<Reservation> reservations = reservationDao.getReservations(customerId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer_reservations.jsp");
            request.setAttribute("reservation_list", reservations);
            dispatcher.forward(request, response);
        } else {
            List<Reservation> reservations = reservationDao.getReservations(user.getUserId());
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer_homepage.jsp");
            request.setAttribute("reservation_list", reservations);
            dispatcher.forward(request, response);
        }
    }
*/
    private void loadVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception{

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicles = vehicleDao.getVehicles();
        request.setAttribute("vehicles_list", vehicles);

    }
/*
    private void addReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        loadVehicles(request, response);
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
*/
    private void errorMessage(HttpServletResponse response) throws IOException{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("You're in the wrong neighborhood son!");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
