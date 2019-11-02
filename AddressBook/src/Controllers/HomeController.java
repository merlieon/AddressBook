package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    Button displayCustomerBtn;

    @FXML
    public void DisplayCustomersView() throws IOException {

       /* Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();*/
    }

    @FXML
    public void DisplayAddCustomerView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/AddCustomerView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void DisplayDeletedCustomersView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/DeletedCustomersView.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
