package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class LoginController {

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXTextField usernameInput;

    @FXML
    private JFXPasswordField passwordInput;

    @FXML
    private JFXButton RegisterButton;

    private AccountFactory accountFactory = AccountFactory.getFactory();

    @FXML
    void AttemptLogin(ActionEvent event) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

    }

    @FXML
    void switchToMainMenu(ActionEvent event) {

    }

    @FXML
    void switchToRegister(ActionEvent event) {

    }
}
