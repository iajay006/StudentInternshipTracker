package com.internship.servlet;

import com.internship.dao.UserDAO;
import com.internship.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null
                || email.isBlank() || password.isBlank()) {
            response.sendError(400, "Please enter email and password.");
            return;
        }

        try {
            User user = userDAO.loginUser(email.trim(), password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());

                response.sendRedirect("dashboard");
            } else {
                response.sendRedirect("login.html?error=1");
            }

        } catch (SQLException e) {
            throw new ServletException("Login failed.", e);
        }
    }
}