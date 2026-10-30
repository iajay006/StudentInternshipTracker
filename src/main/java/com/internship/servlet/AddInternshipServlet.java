package com.internship.servlet;

import com.internship.dao.InternshipDAO;
import com.internship.model.Internship;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add-internship")
public class AddInternshipServlet extends HttpServlet {

    private final InternshipDAO internshipDAO = new InternshipDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String company = request.getParameter("company");
        String role = request.getParameter("role");
        String location = request.getParameter("location");
        String stipendText = request.getParameter("stipend");
        String status = request.getParameter("status");
        String appliedDate = request.getParameter("appliedDate");
        String deadline = request.getParameter("deadline");
        String notes = request.getParameter("notes");

        if (company == null || role == null
                || company.isBlank() || role.isBlank()) {
            response.sendError(400, "Company and role are required.");
            return;
        }

        try {
            double stipend = (stipendText == null || stipendText.isBlank())
                    ? 0 : Double.parseDouble(stipendText);

            if (!Double.isFinite(stipend) || stipend < 0) {
                response.sendError(400, "Enter a valid stipend.");
                return;
            }

            Internship internship = new Internship();
            internship.setUserId((Integer) session.getAttribute("userId"));
            internship.setCompany(company.trim());
            internship.setRole(role.trim());
            internship.setLocation(location);
            internship.setStipend(stipend);
            internship.setStatus(
                    status == null || status.isBlank() ? "Applied" : status);
            internship.setAppliedDate(appliedDate);
            internship.setDeadline(deadline);
            internship.setNotes(notes);

            boolean saved = internshipDAO.addInternship(internship);

            if (saved) {
                response.sendRedirect("internships?added=1");
            } else {
                response.sendError(500, "Could not save internship.");
            }

        } catch (NumberFormatException e) {
            response.sendError(400, "Stipend must be a valid number.");
        } catch (IllegalArgumentException e) {
            response.sendError(400, "Please enter valid dates.");
        } catch (SQLException e) {
            throw new ServletException("Could not save internship.", e);
        }
    }
}