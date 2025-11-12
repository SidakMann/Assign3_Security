/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpagehelper;

import java.sql.*;

/**
 *
 * @author john
 */
public class MainPageHelper {
    
    public String getUserTable(String username) throws ClassNotFoundException, SQLException
    {
        boolean userIsAdmin=isAdmin(username);
                
        String userTable="<table border='1'><th>Username</th>";
        
        if (userIsAdmin) {
            userTable = userTable + "<th>Delete</th>";
        }
        
       
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/assign3users","root","admin123");
        
        String sql = "select * from users;";
        
        Statement st = conn.createStatement();
        
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next())
        {
            
            userTable = userTable + "<tr><td>" + rs.getString(1) + "</td>";
            
            if (userIsAdmin)
                userTable = userTable + "<td><a href='UserServices?delete=" 
                    + rs.getString(1) + "'>Delete</a></td>";
            
            userTable = userTable + "</tr>";
            
        }
        
        userTable += "</table>";
        
        rs.close();
        st.close();
        conn.close();
        
        return userTable;
    }
    
    private boolean isAdmin(String username) throws ClassNotFoundException, SQLException
    {
        boolean isAdmin=false; 
        
        Class.forName("org.mariadb.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/assign3users","root","admin123");
        
        String sql = "select admin from users where username='" + username + "';";
        
        Statement st = conn.createStatement();
        
        ResultSet rs = st.executeQuery(sql);
        
        if (rs.next())
        {
            isAdmin=rs.getBoolean(1);
        }
        
        rs.close();
        st.close();
        conn.close();
        
        return isAdmin;
    }
    
}
