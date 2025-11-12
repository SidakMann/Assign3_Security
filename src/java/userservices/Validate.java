package userservices;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Validate", urlPatterns = {"/Validate"})
public class Validate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String logout = request.getParameter("logout");

        if (logout != null) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp?message=Logged out");
        } else if (username != null && password != null && !username.equals("") && !password.equals("")) {
            if (validate(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.setContentType("text/html;charset=UTF-8");
                response.sendRedirect("mainpage.jsp");
            } else {
                response.sendRedirect("index.jsp?message=Invalid username or password!");
            }
        } else {
            response.sendRedirect("index.jsp?message=Both username and password are required!");
        }
    }

    private boolean validate(String username, String password) throws ClassNotFoundException, SQLException {
        boolean valid = false;

        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/assign3users",
                "root",
                "admin123"
        );

        String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "';";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            valid = true;
        }

        rs.close();
        st.close();
        conn.close();

        return valid;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login validation servlet";
    }
}
