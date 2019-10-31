package Controllers;

import Database.DatabaseUtility;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class LoginController {

    // variables
    @FXML
    TextField usernameTextField;
    @FXML
    TextField hostnameTextField;
    @FXML
    TextField passwordTextField;

    // Sending Login request
    @FXML
    void Login() {

        DatabaseUtility databaseUtility = new DatabaseUtility(hostnameTextField.getText(), usernameTextField.getText(),passwordTextField.getText());
        databaseUtility.Connection();

    }
}
