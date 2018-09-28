package databases;

import java.sql.*;

public class TryConnection {

    public static Connection connectToDB(String username, String password, String url) {

        try {
            //Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
            System.exit(1);
        }
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Ooops, couldn't get a connection");
            System.out.println("Check that <username> & <password> are right");
            System.exit(1);
        }

        if(conn == null){
            System.out.println("Failed to make connection");
            System.exit(1);
        }
        
        return conn;
        
    }

}