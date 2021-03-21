package com.rental.servlet;

import com.rental.dao.UserDao;
import com.rental.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserControllerServlet", value = "/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Getting the command from the request
            String commandId = request.getParameter("command");

            if(commandId == null)
                commandId = "LIST";

            // Dispatch
            switch (commandId) {

                case "LOGIN":
                    login(request, response);
                    break;

                case "LOGOUT":
                    logout(request, response);
                    break;

                case "LOAD":
                    loadUser(request, response);
                    break;

                case "PROFILE":
                    loadProfile(request, response);
                    break;

                default:
                    listCustomer(request, response);
                    break;
            }


        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    //Get Methods

    protected void login(HttpServletRequest request, HttpServletResponse response) {

        String username= request.getParameter("username");
        String password = request.getParameter("userpassword");

        //Test
        //System.out.println(email + " " + password);

        UserDao userDao = new UserDao();

        try {
            User theUser = userDao.checkLogin(username, password);
            String destination = "login.jsp";

            if (theUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", theUser);
                session.setAttribute("command", "LIST");
                if (theUser.isAdmin())
                    destination = "UserControllerServlet";
                else
                    destination = "CarParkControllerServlet";
            } else {
                response.setContentType("text/html");
                String message = "Invalid user/pass";
                PrintWriter out = response.getWriter();
                out.print(message);
            }

            System.out.println(destination);

            response.sendRedirect(destination);

        } catch (Exception e)  {
            e.printStackTrace();
        }

    }

    private void loadUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userId = request.getParameter("userId");

        UserDao userDao = new UserDao();
        User user = userDao.getUser(userId);

        request.setAttribute("theUser", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_user.jsp");
        dispatcher.forward(request, response);

    }

    private void listCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDao userDao = new UserDao();
        List<User> customers = userDao.getCustomers();
        request.setAttribute("customerList", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_homepage.jsp");
        dispatcher.forward(request, response);

    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        request.getRequestDispatcher("login.jsp").include(request, response);

        HttpSession session = request.getSession();
        session.invalidate();

        out.print("You have successfully logged out!");

        out.close();

    }

    private void loadProfile(HttpServletRequest request, HttpServletResponse response) throws Exception{

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_profile.jsp");
        dispatcher.forward(request, response);

    }

    /*
    private void errorPage(HttpServletRequest request, HttpServletResponse response, String commandId) throws ServletException, IOException{

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.print("<html><body>");
        out.print(commandId);
        out.print("</body></html>");

    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandId = request.getParameter("command");

        if (commandId == null)
            commandId = "ERROR";

        try {
            switch (commandId) {
                case "REG":
                    customerRegister(request, response);
                    break;

                case "DELETE":
                    deleteCustomer(request, response);
                    break;

                case "UPDATE":
                    updateUser(request, response);
                    break;

                default:
                    errorMessage(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // TODO: Check from where are you from

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User upUser = userDao.getUser(request.getParameter("userId"));

        userDao.updateCustomer(upUser, firstName, lastName, email, username, password);

        User currentUser =(User) request.getSession().getAttribute("user");

        if (currentUser.isAdmin())
            response.sendRedirect("UserControllerServlet");
        else
            response.sendRedirect("CarParkControllerServlet");

    }

    private void customerRegister (HttpServletRequest request, HttpServletResponse response) throws Exception {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User newUser = new User(firstName, lastName, email, username, password, null);
        UserDao newUserDao = new UserDao();

        newUserDao.saveCustomer(newUser);

        response.sendRedirect("UserControllerServlet");

    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String customerId = request.getParameter("customerId");

        UserDao userDao = new UserDao();

        userDao.deleteCustomer(customerId);

        response.sendRedirect("UserControllerServlet");

    }

    private void errorMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        request.getRequestDispatcher("login.jsp").include(request, response);

        out.print("Error");

        out.close();

    }

}
