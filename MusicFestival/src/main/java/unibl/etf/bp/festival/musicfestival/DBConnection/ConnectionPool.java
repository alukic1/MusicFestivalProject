package unibl.etf.bp.festival.musicfestival.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

    private static Connection instance = null;

    private ConnectionPool() throws SQLException {

       try{
           instance = DriverManager.getConnection("jdbc:mysql://localhost:3306/festivaldb", "USER", "PASSWORD");
       }
       catch(SQLException e){
           e.printStackTrace();
       }

    }
    public static Connection getInstance() throws SQLException {
        if(instance == null) {
            try {
                new ConnectionPool();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static void close() throws SQLException {
        instance.close();
    }
}
