<%
    // Check if session exists and username is set
    String username = (String) session.getAttribute("username");
    if (username == null || username.trim().isEmpty()) {
        response.sendRedirect("index.jsp?message=Please login first");
        return;
    }

    String message = request.getParameter("message");
%>

<jsp:useBean id="helper" class="mainpagehelper.MainPageHelper" />


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

  
    </head>
    <body class="bg-light">
    <body class="bg-light">
        <div class="container mt-5 p-4 bg-white rounded shadow">
            <h1 class="text-center text-primary mb-4">Manage Users</h1>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <p class="mb-0">Welcome, <b><%= session.getAttribute("username") %></b></p>
                <a href="Validate?logout=true" class="btn btn-outline-danger btn-sm">Logout</a>
            </div>

            <<h2>Add User</h2>
<form action="UserServices" method="POST">
    Username: <input type="text" name="username" required><br/>
    Password: <input type="password" name="password" required><br/>
    <input type="submit" value="Add User">
</form>


            <% if (message != null) { %>
            <div class="alert alert-info text-center"><%= message %></div>
            <% } %>

            <h4>List of Users</h4>
            <%= helper.getUserTable((String)session.getAttribute("username")) %>
        </div>
    </body>

        
    </body>
</html>
