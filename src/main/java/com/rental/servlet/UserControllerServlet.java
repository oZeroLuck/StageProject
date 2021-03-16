package com.rental.servlet;

import com.rental.dao.UserDao;
import com.rental.entity.User;
import org.hibernate.HibernateException;

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

            // Dispatch
            switch (commandId) {
                case "REG":
                    userRegister(request, response);

                case "LOGOUT":
                    logout(request, response);

                default:
                    errorPage(request, response, commandId);
            }


        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    //TODO: Check for user type
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email= request.getParameter("username");
        String password = request.getParameter("userpassword");

        //Test
        System.out.println(email + " " + password);

        UserDao userDao = new UserDao();

        try {
            User theUser = userDao.checkLogin(email, password);
            String destination = "login.jsp";

            if (theUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", theUser);
                session.setAttribute("command", "LIST");
                destination = "CarParkControllerServlet";
            } else {
                String message = "Invalid user/pass";
                request.setAttribute("message", message);
            }

            response.sendRedirect(destination);
        } catch (Exception e)  {
            throw new ServletException(e);
        }


    }

    //Methods

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserDao userDao = new UserDao();
        List<User> users = userDao.getCustomers();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
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

    private void userRegister (HttpServletRequest request, HttpServletResponse response) throws Exception {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User newUser = new User(firstName, lastName, email, password, false, null);
        UserDao newUserDao = new UserDao();

        newUserDao.saveCustomer(newUser);

        listUsers(request, response);

    }

    private void errorPage(HttpServletRequest request, HttpServletResponse response, String commandId) throws ServletException, IOException{

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.print("<html><body>");
        out.print(commandId);
        out.print("</body></html>");

    }

}
