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
                case "LOAD_VS":
                    listAvailableVehicles(request, response);
                    break;

                case "LOAD_R":
                    loadReservation(request, response);
                    break;

                case "CAR_PARK":
                    carPark(request, response);
                    break;

                case "LOAD_V":
                    loadVehicle(request, response);
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

        // Branch between admin and customer types
        if (user.isAdmin()) {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            List<Reservation> reservations = reservationDao.getUserReservations(customerId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer_reservations.jsp");
            request.setAttribute("reservation_list", reservations);
            dispatcher.forward(request, response);
        } else {
            List<Reservation> reservations = reservationDao.getUserReservations(user.getUserId());
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer_homepage.jsp");
            request.setAttribute("reservation_list", reservations);
            dispatcher.forward(request, response);
        }
    }

    private List<Vehicle> loadVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicles = vehicleDao.getVehicles();
        return vehicles;

    }

    //TODO: Change way of checking available vehicle to compare dates
    private void listAvailableVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception{

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicleList = vehicleDao.available();

        // Setting the attribute and dispatching to jsp
        request.setAttribute("vehicles_list", vehicleList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_reservation.jsp");
        dispatcher.forward(request, response);

    }

    private void loadVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Get the vehicle Id
        String vehicleId = request.getParameter("vehicleId");

        // Load the vehicle
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle theVehicle = vehicleDao.getTheVehicle(vehicleId);

        // Place vehicle in request attribute
        request.setAttribute("theVehicle", theVehicle);

        // Send to jsp page
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("update_vehicle.jsp");
        dispatcher.forward(request, response);

    }


    private void carPark(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Vehicle> vehicles = loadVehicles(request, response);
        request.setAttribute("vehicles_list", vehicles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_park.jsp");
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
                case "ADD_R":
                    addReservation(request, response);
                    break;

                case "ADD_V":
                    addVehicle(request, response);
                    break;

                case "DELETE":
                    deleteReservation(request, response);
                    break;

                case "UPDATE_R":
                    updateReservation(request, response);
                    break;

                case "UPDATE_V":
                    updateVehicle(request, response);

                case "DECIDE":
                    approveReservation(request, response);
                    break;

                case "DELETE_V":
                    deleteVehicle(request, response);
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
    //TODO: Check the dates!
    private void addReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //Get user from session
        HttpSession session = request.getSession();
        User currentUser =(User) session.getAttribute("user");

        //Get vehicle id
        String vehicleId = request.getParameter("selected");

        //Set date
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        //Get vehicle from id
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle theVehicle = vehicleDao.getTheVehicle(vehicleId);


        Reservation theReservation = new Reservation(theVehicle, startDate, endDate, currentUser);
        vehicleDao.updateVehicle(theVehicle ,true);
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

    //TODO: Check the dates!
    private void updateReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        // Getting the values
        String reservationId = request.getParameter("reservationId");
        String newStartDate = request.getParameter("newStartDate");
        String newEndDate = request.getParameter("newEndDate");

        // Get the reservation
        ReservationDao reservationDao = new ReservationDao();
        Reservation updatedReservation = reservationDao.getTheReservation(reservationId);

        // Update it
        reservationDao.updateReservation(updatedReservation, newStartDate, newEndDate);

        // Redirect
        response.sendRedirect("CarParkControllerServlet");

    }

    private void approveReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        // Get reservation
        ReservationDao reservationDao = new ReservationDao();
        Reservation reservation = reservationDao.getTheReservation(request.getParameter("reservationId"));

        reservationDao.approveReservation(reservation, request.getParameter("verdict"));

        response.sendRedirect("UserControllerServlet");
    }

    //Vehicles
    //TODO: Check that the car is not inserted
    private void addVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception{

        // Getting parameters
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String plate = request.getParameter("plate");
        String type = request.getParameter("type");

        Vehicle vehicle = new Vehicle(type, plate, model, brand);
        VehicleDao vehicleDao = new VehicleDao();

        vehicleDao.saveVehicle(vehicle);

        carPark(request, response);

    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String vehicleId = request.getParameter("vehicleId");

        VehicleDao vehicleDao = new VehicleDao();

        vehicleDao.delete(vehicleId);

        carPark(request, response);

    }

    private void updateVehicle (HttpServletRequest request, HttpServletResponse response) throws Exception{

        // Getting parameters
        String type = request.getParameter("type");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String plate = request.getParameter("plate");

        // Getting the vehicle
        VehicleDao vehicleDao = new VehicleDao();
        Vehicle vehicle = vehicleDao.getTheVehicle(request.getParameter("vehicleId"));

        // Update
        vehicleDao.updateVehicle(vehicle, type, brand, model, plate);

        carPark(request, response);

    }

}

/*





 */
