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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/internships")
public class InternshipsServlet extends HttpServlet {

    private final InternshipDAO internshipDAO = new InternshipDAO();

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

        try {
            List<Internship> internships =
                    internshipDAO.getInternshipsByUserId(userId);

            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
                out.println("<title>My Internships</title>");

                out.println("<style>");
                out.println("body{font-family:Arial;background:#f3f4f6;margin:0;padding:25px;color:#222;}");
                out.println(".container{max-width:1100px;margin:auto;background:white;padding:25px;border-radius:12px;box-shadow:0 4px 15px #ddd;}");
                out.println("h1{color:#4338ca;}");
                out.println(".table-wrap{overflow-x:auto;}");
                out.println("table{width:100%;border-collapse:collapse;margin-top:20px;}");
                out.println("th,td{padding:12px;border:1px solid #ddd;text-align:left;}");
                out.println("th{background:#4338ca;color:white;}");
                out.println("tr:nth-child(even){background:#f9fafb;}");
                out.println(".btn{display:inline-block;margin:10px 8px 10px 0;padding:11px 16px;background:#4338ca;color:white;text-decoration:none;border-radius:6px;}");
                out.println(".empty{padding:20px;background:#f9fafb;border-radius:8px;}");
                out.println("</style></head><body>");

                out.println("<div class='container'>");
                out.println("<h1>My Internships</h1>");

                out.println("<a class='btn' href='dashboard'>Dashboard</a>");
                out.println("<a class='btn' href='add-internship.html'>Add Internship</a>");

                if (request.getParameter("added") != null) {
                    out.println("<p style='color:green;'>Internship added successfully!</p>");
                }

                if (internships.isEmpty()) {

                    out.println("<div class='empty'>");
                    out.println("<p>No internships found. Add your first internship!</p>");
                    out.println("</div>");

                } else {

                    out.println("<div class='table-wrap'>");
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<th>Company</th>");
                    out.println("<th>Role</th>");
                    out.println("<th>Location</th>");
                    out.println("<th>Stipend</th>");
                    out.println("<th>Status</th>");
                    out.println("<th>Applied Date</th>");
                    out.println("<th>Deadline</th>");
                    out.println("<th>Notes</th>");
                    out.println("</tr>");

                    for (Internship internship : internships) {

                        out.println("<tr>");
                        out.println("<td>" + escapeHtml(internship.getCompany()) + "</td>");
                        out.println("<td>" + escapeHtml(internship.getRole()) + "</td>");
                        out.println("<td>" + escapeHtml(internship.getLocation()) + "</td>");
                        out.println("<td>₹" + internship.getStipend() + "</td>");
                        out.println("<td>" + escapeHtml(internship.getStatus()) + "</td>");
                        out.println("<td>" + escapeHtml(internship.getAppliedDate()) + "</td>");
                        out.println("<td>" + escapeHtml(internship.getDeadline()) + "</td>");
                        out.println("<td>" + escapeHtml(internship.getNotes()) + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("</div>");
                }

                out.println("</div></body></html>");
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to load internships.", e);
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}