package Database;
import java.sql.*;

public class DatabaseUtility {

    // Constructor
   public DatabaseUtility(String hostname, String username, String password){
       this.hostname = hostname;
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
            con = DriverManager.getConnection("jdbc:mysql://"+ hostname +"/addressbook", username, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Using existing connected database
    public static Connection getConnection(){
        return con;
    }

}
