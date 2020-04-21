package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.ModelClasses.Account.PasswordHasher;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class LoginController {

  public Label loginText;
  @FXML private JFXButton loginButton;

  @FXML private JFXTextField usernameInput;

  @FXML private JFXPasswordField passwordInput;

  @FXML private Label incorrectLabel; // label that is displayed if teh input is not valid

  private AccountFactory accountFactory = AccountFactory.getFactory();

  SceneController sceneController = App.getSceneController(); // used to switch between scenes

  @FXML
  void enableLogin(KeyEvent event) { // called on each key release for both inputs
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    if (!username.isEmpty() && !password.isEmpty()) {
      loginButton.setDisable(
          false); // the login button is enabled only when there is text in the two input
    } else {
      loginButton.setDisable(true); // checks if the user entered text and then deleted the text
    }
  }

  @FXML
  void attemptLogin(ActionEvent event) throws Exception {
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    try {
      if (PasswordHasher.verifyPassword(
          password, accountFactory.getPasswordByUsername(username))) { // does the password match
        incorrectLabel.setText("");
        System.out.println("The account is valid");
        switchToMainMenu2();
        // code that logs the user into the application
      } else {
        incorrectLabel.setText("The username or password is incorrect");
        usernameInput.setUnFocusColor(Color.RED);
        passwordInput.setUnFocusColor(Color.RED); // sets the unfocused colors to red
        passwordInput.setText("");
      }
    } catch (Exception e) { // no existing account in the db
      incorrectLabel.setText("The username or password is incorrect");
      usernameInput.setUnFocusColor(Color.RED);
      passwordInput.setUnFocusColor(Color.RED); // sets the unfocused colors to red
      passwordInput.setText("");
    }
  }

  @FXML
  void switchToMainMenu(ActionEvent event) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  @FXML
  void switchToMainMenu2() throws IOException {
    sceneController.switchScene("MainMenu");
  }

  @FXML
  void switchToRegister(ActionEvent event) throws IOException {
    sceneController.switchScene("Register");
  }
}
