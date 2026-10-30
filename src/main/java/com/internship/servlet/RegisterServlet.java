package com.internship.servlet;

import com.internship.dao.UserDAO;
import com.internship.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || email == null || password == null
                || name.isBlank() || email.isBlank() || password.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Please fill in all fields.");
            return;
        }

        try {
            User user = new User(name.trim(), email.trim(), password);
            boolean registered = userDAO.registerUser(user);

            if (registered) {
                response.sendRedirect("login.html?registered=1");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Registration failed.");
            }

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                response.sendError(HttpServletResponse.SC_CONFLICT,
                        "This email may already be registered.");
            } else {
                throw new ServletException("Registration failed.", e);
            }
        }
    }
}