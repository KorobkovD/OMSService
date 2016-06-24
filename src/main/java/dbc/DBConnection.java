package dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection class
 */
public class DBConnection {
    private static Connection conn;
    private static String url = "jdbc:mysql://localhost:3306/oms";
    private static String user = "root";
    private static String pass = "root";

    /**
     * Connect to the database
     * @return - The instance of Connection
     * @throws SQLException
     */
    public static Connection connect() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException cnfe){
            System.err.println("Error: "+cnfe.getMessage());
        }catch(InstantiationException ie){
            System.err.println("Error: "+ie.getMessage());
        }catch(IllegalAccessException iae){
            System.err.println("Error: "+iae.getMessage());
        }
        conn = DriverManager.getConnection(url,user,pass);
        return conn;
    }

    /**
     * Connection getter
     * @return - The instance of Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;
    }
}