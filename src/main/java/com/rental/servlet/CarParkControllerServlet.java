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
                case "REQUEST":
                    loadRequestPage(request, response);
                    break;

                case "CAR_PARK":
                    carPark(request, response);
                    break;

                case "REQ_V":
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
            userId = Integer.parseInt(request.getParameter("userId"));
            request.setAttribute("userId", userId);
            dispatcher = request.getRequestDispatcher("customer_reservations.jsp");
        } else {
            userId = user.getUserId();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = format.parse(format.format(new Date()));
            request.setAttribute("today", today);
            dispatcher = request.getRequestDispatcher("customer_homepage.jsp");
        }
        List<Reservation> reservations = reservationDao.getUserReservations(userId, filter, value);
        request.setAttribute("reservation_list", reservations);
        dispatcher.forward(request, response);
    }

    private void loadRequestPage(HttpServletRequest request, HttpServletResponse response) throws Exception{

        boolean secondCommand = request.getParameter("secondCommand").equals("ADD");
        request.setAttribute("secondCommand", request.getParameter("secondCommand"));
;
        DateFormat format = new SimpleDateFormat();
        String today = format.format(Calendar.getInstance().getTime());
        request.setAttribute("now", today);
        VehicleDao vehicleDao = new VehicleDao();

        if(secondCommand) {
            List<Vehicle> vehicles = vehicleDao.available();
            request.setAttribute("vehicles_list", vehicles);
        }

        if(!secondCommand) {

            // Get the reservation id
            String reservationId = request.getParameter("reservationId");
            List<Vehicle> vehicles = vehicleDao.getVehicles();
            request.setAttribute("vehicles_list", vehicles);

            // Getting the reservation
            ReservationDao reservationDao = new ReservationDao();
            Reservation theReservation = reservationDao.getTheReservation(reservationId);

            // Place the reservation in request attribute
            request.setAttribute("theReservation", theReservation);
            request.setAttribute("sDateValue", theReservation.getStartDate());
            request.setAttribute("eDateValue", theReservation.getEndDate());

        }

        // Send to jsp page
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("add_reservation_request.jsp");
        dispatcher.forward(request, response);

    }

    private void loadVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get the second command
        boolean isAdd = !(request.getParameter("secondCommand") == null);

        if(isAdd)
            request.setAttribute("isAdd", true);

        else {
            // Get the vehicle Id
            String vehicleId = request.getParameter("vehicleId");

            // Load the vehicle
            VehicleDao vehicleDao = new VehicleDao();
            Vehicle theVehicle = vehicleDao.getTheVehicle(vehicleId);

            // Place vehicle in request attribute
            request.setAttribute("theVehicle", theVehicle);
        }
        // Send to jsp page
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("vehicle_request.jsp");
        dispatcher.forward(request, response);

    }


    private void carPark(HttpServletRequest request, HttpServletResponse response) throws Exception {

        VehicleDao vehicleDao = new VehicleDao();
        List<Vehicle> vehicles = vehicleDao.getVehicles();
        request.setAttribute("vehicles_list", vehicles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("car_park.jsp");
        dispatcher.forward(request, response);

    }

    private void loadReservation(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //Get the reservation id
        String reservationId = request.getParameter("reservationId");
        System.out.println(reservationId);

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

                case "V_ACTION":
                    actionVehicle(request, response);
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
                    RequestDispatcher dispatcher = request.getRequestDispatcher("error_page.jsp");
                    dispatcher.forward(request, response);
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

        // Check if dates are empty
        if (!(startDate.equals("") || endDate.equals(""))) {
            // Converting dates
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            Date today = format.parse(format.format(new Date()));

            // Checking the date validity
            if (sDate.before(eDate) && (sDate.after(today) || sDate.equals(today))) {
                // Setting the reservation DAO
                ReservationDao reservationDao = new ReservationDao();

                // Getting the vehicle
                VehicleDao vehicleDao = new VehicleDao();
                Vehicle vehicle = vehicleDao.getTheVehicle(request.getParameter("selected"));

                // Setting reservation
                Reservation oldReservation = null;

                // Update reservation date check
                if (!secondCommand) {
                    oldReservation = reservationDao.getTheReservation(
                            request.getParameter("reservationId"));
                    Date oldStartDate = oldReservation.getStartDate();
                    Date oldEndDate = oldReservation.getEndDate();

                    // Update dates not valid - I know it's too long :(
                    if (!(sDate.after(oldStartDate) && (sDate.after(today) || sDate.equals(today))) || sDate.equals(oldStartDate) && eDate.equals(oldEndDate))
                        errorFlag = true;

                } else
                    // Check if vehicle is not selected from add
                    if (vehicle == null)
                        errorFlag = true;
                if (!errorFlag) {
                    if (secondCommand) {
                        Reservation reservation = new Reservation(vehicle, sDate, eDate, currentUser);
                        reservationDao.saveReservation(reservation);
                    } else {
                        reservationDao.requestUpdate(oldReservation, sDate, eDate, vehicle);
                    }
                    response.sendRedirect("CarParkControllerServlet");
                    return;
                }
            }

        }
            RequestDispatcher dispatcher = request.getRequestDispatcher("error_page.jsp");
            dispatcher.forward(request, response);

    }





                session.setAttribute("sessionCommand", sessionCommand);
                session.setAttribute("message", "Invalid dates");

                response.sendRedirect("CarParkControllerServlet");
            }
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

        ReservationDao reservationDao = new ReservationDao();
        Reservation reservation = reservationDao.getTheReservation(request.getParameter("reservationId"));

        reservationDao.approveReservation(reservation, request.getParameter("verdict"));

        listReservation(request, response);
        //response.sendRedirect("UserControllerServlet");
    }

    //Vehicles
    private void actionVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception{

        // Getting parameters
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String plate = request.getParameter("plate");
        String type = request.getParameter("type");

        VehicleDao vehicleDao = new VehicleDao();

        if(request.getParameter("secondCommand") == null) {

            Vehicle vehicle = vehicleDao.getTheVehicle(request.getParameter("vehicleId"));
            vehicleDao.updateVehicle(vehicle, type, brand, model, plate);

        } else {

            Vehicle vehicle = new Vehicle(type, plate, model, brand);
            vehicleDao.saveVehicle(vehicle);

        }

        carPark(request, response);

    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String vehicleId = request.getParameter("vehicleId");

        VehicleDao vehicleDao = new VehicleDao();

        vehicleDao.delete(vehicleId);

        carPark(request, response);

    }


}