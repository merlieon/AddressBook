package Controllers;

import Database.DatabaseUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class LoginController {

    // variables
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    Button loginBtn;

    // Sending Login request
    @FXML
    void LoginButtonPush(ActionEvent event) throws IOException {

        // Connecting towards database
        DatabaseUtility databaseUtility = new DatabaseUtility(usernameTextField.getText(), passwordTextField.getText());
        databaseUtility.Connection();

        // Opening new window if connected
        try {
            if (!DatabaseUtility.getConnection().isClosed()){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/HomeView.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(new Scene(root));
                stage.show();

                // Selecting currentStage
                Stage currentStage = (Stage) loginBtn.getScene().getWindow();

                // Closing current stage
                currentStage.close();
            }
        } catch (Exception e) {
            // Displaying alert box
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Alert");
            alert.setContentText("Login failed. Please check your credentials.");
            alert.show();
        }
    }
}