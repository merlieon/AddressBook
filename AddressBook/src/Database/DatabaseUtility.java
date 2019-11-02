package Database;
import java.sql.*;

public class DatabaseUtility {

    // Constructor
   public DatabaseUtility(String username, String password){
       this.username = username;
       this.password = password;
    }

    // Variables
    private String hostname;
    private String username;
    private String password;
    private static Connection con;

    // Connecting to database
    public void Connection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://192.168.2.71/addressbook", username, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println("Could not connect to database");
        }

    }

    // Using existing connected database
    public static Connection getConnection(){
        return con;
    }
}
