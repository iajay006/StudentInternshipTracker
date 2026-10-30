package com.internship.servlet;

import com.internship.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        String sql = """
            SELECT
                COUNT(*) AS total,
                COALESCE(SUM(LOWER(status) = 'applied'), 0) AS applied,
                COALESCE(SUM(LOWER(status) = 'selected'), 0) AS selected_count,
                COALESCE(SUM(LOWER(status) = 'rejected'), 0) AS rejected
            FROM internships
            WHERE user_id = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    request.setAttribute("total", rs.getInt("total"));
                    request.setAttribute("applied", rs.getInt("applied"));
                    request.setAttribute("selected",
                            rs.getInt("selected_count"));
                    request.setAttribute("rejected",
                            rs.getInt("rejected"));
                }
            }

            request.getRequestDispatcher("/dashboard.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load dashboard statistics.", e);
        }
    }
}
