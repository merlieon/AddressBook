package Controllers;

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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
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

import Database.DatabaseUtility;
import Models.Customer;

public class CustomersController implements Initializable{
	
    @FXML
    TableView<Customer> CustomersTableView;
    @FXML
    TableColumn<Customer,String> firstnameTableColumn;
    @FXML
    TableColumn<Customer,String> lastnameTableColumn;
    @FXML
    TableColumn<Customer,String> phonenumberTableColumn;
    @FXML
    TableColumn<Customer,String> emailTableColumn;

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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstnameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
        lastnameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
        phonenumberTableColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phoneNumber"));
                
        try {
            CustomersTableView.getItems().setAll(listCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Setting cells editable
    @FXML
    public void EditColumns(ActionEvent event) throws IOException {
        CustomersTableView.setEditable(true);
        firstnameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastnameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phonenumberTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        }
    
    //Editing database with input from user in GUI
    @FXML
    public void editCustomers(ActionEvent ae) throws SQLException{
    	CustomersTableView.setEditable(false);
    	
        TablePosition pos = CustomersTableView.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Customer item = CustomersTableView.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String data = (String) col.getCellObservableValue(item).getValue();
        
        ObservableList<Customer> selectedCustomer;
        selectedCustomer = CustomersTableView.getSelectionModel().getSelectedItems();
          
        if(col.getText().equals("Firstname")) {
            PreparedStatement statm = con.prepareStatement("UPDATE customers SET firstname= '"+ data + "' WHERE id= " + selectedCustomer.get(0).getId());
            statm.executeUpdate(); 
        }
        if(col.getText().equals("Lastname")) {
        	PreparedStatement statm = con.prepareStatement("UPDATE customers SET lastname= '"+ data + "' WHERE id= " + selectedCustomer.get(0).getId());
        	statm.executeUpdate();
        }
        if(col.getText().equals("Phonenumber")) {
        	PreparedStatement statm = con.prepareStatement("UPDATE customers SET phonenumber= '"+ data + "' WHERE id= " + selectedCustomer.get(0).getId());
        	statm.executeUpdate();
        }
        if(col.getText().equals("Email")) {
        	PreparedStatement statm = con.prepareStatement("UPDATE customers SET email= '"+ data + "' WHERE id= " + selectedCustomer.get(0).getId());
        	statm.executeUpdate();
        }
    }
    
    //Deleting customers (setting active=0)
    @FXML
    public void deleteCustomers(ActionEvent event) throws SQLException {
    	ObservableList<Customer> selectedCustomer, allCustomers;
        selectedCustomer = CustomersTableView.getSelectionModel().getSelectedItems();
        
        // Getting current date and time
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter dateFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatedDate = dateObj.format(dateFormatObj);
        
    	PreparedStatement statm = con.prepareStatement("UPDATE customers SET deleted_date = '" + formatedDate + "', active= 0 WHERE id= " + selectedCustomer.get(0).getId());
    	statm.executeUpdate();
    	
        allCustomers = CustomersTableView.getItems();
        selectedCustomer = CustomersTableView.getSelectionModel().getSelectedItems();
        selectedCustomer.forEach(allCustomers::remove);
    }

    private ObservableList<Customer> listCustomers() throws SQLException {
        ObservableList<Customer> listCustomers = FXCollections.observableArrayList();
        PreparedStatement statm = con.prepareStatement("SELECT * FROM customers WHERE active = 1");
        ResultSet rs = statm.executeQuery();

        while (rs.next()){
            int id = rs.getInt("id");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            String email = rs.getString("email");
            String phonenumber = rs.getString("phonenumber");

            listCustomers.addAll(new Customer(id, firstname, lastname, email, phonenumber));
        }

        return  listCustomers;
    }
    
}
