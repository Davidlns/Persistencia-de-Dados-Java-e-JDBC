package cadastrobd.model.util;

import java.sql.*;

public class ConectorBD {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;user=loja;password=loja;encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static PreparedStatement getPrepared(String sql, Connection conn) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public static ResultSet getSelect(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {}
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {}
        }
    }
}