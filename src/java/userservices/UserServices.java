package userservices;

import java.sql.*;

public class UserServices {

    private Connection getConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb", "root", "");
    }

    // Get user by ID (patched)
    public ResultSet getUserById(int userId) throws Exception {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps.executeQuery();
    }

    // Get all users (safe)
    public ResultSet getAllUsers() throws Exception {
        Connection conn = getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }

    // Insert (patched)
    public void insertUser(String name, String email) throws Exception {
        Connection conn = getConnection();
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // Delete (patched)
    public void deleteUser(int userId) throws Exception {
        Connection conn = getConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // Update (patched)
    public void updateUser(int id, String name, String email) throws Exception {
        Connection conn = getConnection();
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }
}
