package com.rental.servlet;


//import com.rental.dao.ReservationDao;
import com.rental.dao.UserDao;
import com.rental.dao.VehicleDao;
//import com.rental.entity.Reservation;
import com.rental.entity.User;
import com.rental.entity.Vehicle;
import org.dom4j.util.AttributeHelper;
import sun.rmi.server.Dispatcher;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
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

           /*     case "LOAD":
                    loadReservation(request, response);

                case "ADD":
                    addReservation(request, response);

                case "DELETE":
                    deleteReservation(request, response);

                case "UPDATE":
                    updateReservation(request, response); */

                default:
                    listReservation(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void listReservation(HttpServletRequest request, HttpServletResponse response) throws Exception {



       /* ReservationDao reservationDao = new ReservationDao();
        List<Reservation> reservations = reservationDao.getReservations();
        request.setAttribute("reservation_list", reservations);*/
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer_homepage");
        dispatcher.forward(request, response);

    }
}


   /*private void addReservation(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] c = request.getCookies();

        Date currentDate = new Date();
        Vehicle theVehicle = VehicleDao.getVehicle(request.getParameter("vehicle"));
        User theCustomer = UserDao.getCustomer(c);
        String startDate = currentDate.toString();

        Reservation theReservation = new Reservation(theVehicle, startDate, theCustomer);
        ReservationDao theReservationDao = new ReservationDao();

        theReservationDao.saveReservation(theReservation);

        request.setAttribute("command", "LIST");
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_rental/customer_homepage.jsp");
        dispatcher.forward(request, response);

        }


    private void updateReservation(HttpServletRequest request, HttpServletResponse response) {
    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response) {
    }

    private void loadReservation(HttpServletRequest request, HttpServletResponse response) {
    } */
