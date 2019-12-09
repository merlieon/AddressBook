package Controllers;

import Database.DatabaseUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCustomerController {
    private Connection con = DatabaseUtility.getConnection();
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhonenumber;


    // Displaying home view
    @FXML
    public void DisplayHomeView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/HomeView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    // Displaying Customer view
    @FXML
    public void DisplayCustomersView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/CustomersView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Display all customers");
        stage.setScene(scene);
        stage.show();
    }

    // Displaying add customer view
    @FXML
    public void DisplayAddCustomerView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/AddCustomerView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    // Displaying deleted customer view
    @FXML
    public void DisplayDeletedCustomersView(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/DeletedCustomersView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Deleted Customer View");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void insertCustomer(){
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedDate = dateObj.format(dateFormatObj);

        try {
            Statement stmt = con.createStatement();
            String SQL = "insert into customers (firstname,lastname,email,phonenumber,added_date) values ('" + txtFirstname.getText() +  "','"+ txtLastname.getText()+"','"+txtEmail.getText()+"','"+txtPhonenumber.getText()+"','"+formatedDate+"')";

            stmt.executeUpdate(SQL);
            System.out.println("Inserted!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
