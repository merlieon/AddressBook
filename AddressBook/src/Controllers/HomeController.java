package Controllers;

import Database.DatabaseUtility;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    // Variables
    Connection con = DatabaseUtility.getConnection();

    // FXML Variables
    @FXML
    JFXListView<String> listLastAddedItemsFX;
    @FXML
    JFXListView<String> listLastDeletedItemsFX;

    // Displaying home view
    @FXML
    public void DisplayHomeView(ActionEvent event) throws IOException{
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
    public void Logout(ActionEvent event) throws IOException, SQLException {
        DatabaseUtility.getConnection().close();

        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Deleted Customer View");
        stage.setScene(scene);
        stage.show();
    }

    // Listing the last added items
    @FXML
    public void ListLastAddedItems() throws SQLException {

        ObservableList<String> listLastAddedItems = FXCollections.observableArrayList();
        PreparedStatement statm = con.prepareStatement("SELECT * FROM customers");
        ResultSet rs = statm.executeQuery();

        while (rs.next()){
            listLastAddedItems.add(rs.getString("firstname") +  " " + rs.getString("lastname"));
        }
        listLastAddedItemsFX.setItems(listLastAddedItems);
    }

    // Listing the last deleted items
    @FXML
    public void ListLastDeletedItems() throws SQLException {
        ObservableList<String> listLastDeletedItems = FXCollections.observableArrayList();
        PreparedStatement statm = con.prepareStatement("SELECT * FROM customers WHERE active = 0");
        ResultSet rs = statm.executeQuery();

        while (rs.next()){
            listLastDeletedItems.add(rs.getString("firstname") +  " " + rs.getString("lastname"));
        }
        listLastDeletedItemsFX.setItems(listLastDeletedItems);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ListLastAddedItems();
            ListLastDeletedItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
