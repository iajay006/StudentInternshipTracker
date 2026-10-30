package com.internship.model;

public class Internship {

    private int id;
    private int userId;
    private String company;
    private String role;
    private String location;
    private double stipend;
    private String status;
    private String appliedDate;
    private String deadline;
    private String notes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getStipend() { return stipend; }
    public void setStipend(double stipend) { this.stipend = stipend; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAppliedDate() { return appliedDate; }
    public void setAppliedDate(String appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}