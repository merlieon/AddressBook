package Controllers;

import Database.DatabaseUtility;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DeletedCustomersController implements Initializable {

    // FXML Variables
    @FXML
    TableView<Customer> deletedCustomersTableView;
    @FXML
    TableColumn<Customer,String> firstnameTableColumn;
    @FXML
    TableColumn<Customer,String> lastnameTableColumn;
    @FXML
    TableColumn<Customer,String> phonenumberTableColumn;
    @FXML
    TableColumn<Customer,String> emailTableColumn;

    // Variables
    private Connection con = DatabaseUtility.getConnection();

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

    //Initializing method
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Adding items to Table
        firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
        lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
        phonenumberTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phoneNumber"));
        try {
            deletedCustomersTableView.getItems().setAll(listCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Creating a list to store all Items from database to table
    private ObservableList<Customer> listCustomers() throws SQLException {
        ObservableList<Customer> listDeletedCustomers = FXCollections.observableArrayList();
        PreparedStatement statm = con.prepareStatement("SELECT * FROM customers WHERE active = 0");
        ResultSet rs = statm.executeQuery();

        while (rs.next()){
            int id = rs.getInt("id");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            String email = rs.getString("email");
            String phonenumber = rs.getString("phonenumber");

            listDeletedCustomers.addAll(new Customer(id,firstname, lastname, email, phonenumber));
        }

        return  listDeletedCustomers;
    }

    // Restoring deleted item
    public void RestoreCustomer() throws SQLException {
        // Getting current date and time
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedDate = dateObj.format(dateFormatObj);

        // Restoring customer and added restored_date to current date and time
        ObservableList<Customer> selectedCustomer, allCustomers;
        allCustomers = deletedCustomersTableView.getItems();
        selectedCustomer = deletedCustomersTableView.getSelectionModel().getSelectedItems();
        System.out.println(selectedCustomer.get(0).getId() + " " +selectedCustomer.get(0).getFirstName());
        PreparedStatement statm = con.prepareStatement("UPDATE customers SET restored_date = '" + formatedDate + "', active = 1 WHERE id = " + selectedCustomer.get(0).getId());
        statm.executeUpdate();
        selectedCustomer.forEach(allCustomers::remove);
    }
}
