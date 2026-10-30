package com.internship.dao;

import com.internship.model.Internship;
import com.internship.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InternshipDAO {

    // Add internship to database
    public boolean addInternship(Internship internship)
            throws SQLException {

        String sql = """
            INSERT INTO internships
            (user_id, company, role, location, stipend,
             status, applied_date, deadline, notes)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, internship.getUserId());
            ps.setString(2, internship.getCompany());
            ps.setString(3, internship.getRole());
            ps.setString(4, internship.getLocation());
            ps.setDouble(5, internship.getStipend());
            ps.setString(6, internship.getStatus());

            if (internship.getAppliedDate() == null
                    || internship.getAppliedDate().isBlank()) {
                ps.setNull(7, java.sql.Types.DATE);
            } else {
                ps.setDate(7, java.sql.Date.valueOf(
                        internship.getAppliedDate()));
            }

            if (internship.getDeadline() == null
                    || internship.getDeadline().isBlank()) {
                ps.setNull(8, java.sql.Types.DATE);
            } else {
                ps.setDate(8, java.sql.Date.valueOf(
                        internship.getDeadline()));
            }

            ps.setString(9, internship.getNotes());

            return ps.executeUpdate() == 1;
        }
    }

    // Fetch internships belonging to a particular user
    public List<Internship> getInternshipsByUserId(int userId)
            throws SQLException {

        List<Internship> internships = new ArrayList<>();

        String sql = """
            SELECT * FROM internships
            WHERE user_id = ?
            ORDER BY id DESC
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Internship internship = new Internship();

                    internship.setId(rs.getInt("id"));
                    internship.setUserId(rs.getInt("user_id"));
                    internship.setCompany(rs.getString("company"));
                    internship.setRole(rs.getString("role"));
                    internship.setLocation(rs.getString("location"));
                    internship.setStipend(rs.getDouble("stipend"));
                    internship.setStatus(rs.getString("status"));

                    java.sql.Date appliedDate =
                            rs.getDate("applied_date");

                    if (appliedDate != null) {
                        internship.setAppliedDate(
                                appliedDate.toString());
                    }

                    java.sql.Date deadline =
                            rs.getDate("deadline");

                    if (deadline != null) {
                        internship.setDeadline(
                                deadline.toString());
                    }

                    internship.setNotes(rs.getString("notes"));

                    internships.add(internship);
                }
            }
        }

        return internships;
    }
}