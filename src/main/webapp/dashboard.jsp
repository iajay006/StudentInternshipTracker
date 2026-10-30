<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.html");
        return;
    }

    Object total = request.getAttribute("total");
    Object applied = request.getAttribute("applied");
    Object selected = request.getAttribute("selected");
    Object rejected = request.getAttribute("rejected");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | Student Internship Tracker</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        body { padding: 30px; }
        .container { max-width: 1100px; margin: auto; }
        .subtitle { color: #68758a; margin-bottom: 28px; }
        .buttons { display: flex; gap: 12px; margin-bottom: 28px; flex-wrap: wrap; }
        .cards { display: grid; grid-template-columns: repeat(4, minmax(0, 1fr)); gap: 18px; }
        .card { background: white; padding: 24px; border-radius: 12px; box-shadow: 0 4px 15px #15284a12; border-top: 4px solid #2456b3; }
        .card h3 { font-size: 15px; color: #68758a; margin: 0 0 18px; }
        .number { font-size: 34px; font-weight: bold; color: #2456b3; }
        @media (max-width: 750px) { .cards { grid-template-columns: repeat(2, minmax(0, 1fr)); } }
        @media (max-width: 420px) { .cards { grid-template-columns: 1fr; } }
    </style>
</head>
<body>
<div class="container">
    <h1>Student Dashboard</h1>
    <p class="subtitle">Manage and track your internship applications.</p>

    <div class="buttons">
        <a class="btn" href="index.html">Home</a>
        <a class="btn" href="internships">My Internships</a>
        <a class="btn" href="add-internship.html">+ Add Internship</a>
    </div>

    <div class="cards">
        <div class="card">
            <h3>Total Internships</h3>
            <div class="number"><%= total != null ? total : 0 %></div>
        </div>
        <div class="card">
            <h3>Applied</h3>
            <div class="number"><%= applied != null ? applied : 0 %></div>
        </div>
        <div class="card">
            <h3>Selected</h3>
            <div class="number"><%= selected != null ? selected : 0 %></div>
        </div>
        <div class="card">
            <h3>Rejected</h3>
            <div class="number"><%= rejected != null ? rejected : 0 %></div>
        </div>
    </div>
</div>
</body>
</html>