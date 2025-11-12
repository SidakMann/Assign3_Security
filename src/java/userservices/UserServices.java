/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userservices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author john
 */
@WebServlet(name = "UserServices", urlPatterns = {"/UserServices"})
public class UserServices extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String delete=request.getParameter("delete");
        
        //Handle adding a user
        if (username!=null && password!=null && !username.equals("") && !password.equals(""))
        {
            String sql = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
            
            runQuery(sql);
            
            response.sendRedirect("mainpage.jsp?message=User added to database");
            
            
        }
        //Handle deleting user
        else if (delete!=null)
        {
            String sql = "delete from users where username='" + delete + "'";
            
            runQuery(sql);
            
            response.sendRedirect("mainpage.jsp?message=User deleted from database");
        }
        
    }

    public void runQuery(String sql) throws ClassNotFoundException, SQLException
    {
        Class.forName("org.mariadb.jdbc.Driver");
        
        //Open special database connection...
        Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/assign3users?allowMultiQueries=true","root","admin123");
        
        Statement st = conn.createStatement();
        
        st.executeUpdate(sql);
        
        st.close();
        conn.close();

    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
