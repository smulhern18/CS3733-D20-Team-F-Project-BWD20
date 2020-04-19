package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class LoginController {

  @FXML private JFXButton loginButton;

  @FXML private JFXTextField usernameInput;

  @FXML private JFXPasswordField passwordInput;

  @FXML private JFXButton registerButton;

  @FXML private Label incorrectLabel;

  private AccountFactory accountFactory = AccountFactory.getFactory();

  SceneController sceneController = App.getSceneController();

  @FXML
  void enableLogin(KeyEvent event) {
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    if (!username.isEmpty() && !password.isEmpty()) {
      loginButton.setDisable(false);
    } else {
      loginButton.setDisable(true);
    }
  }

  @FXML
  void attemptLogin(ActionEvent event) throws InterruptedException {
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    Account account = accountFactory.getAccountByUsername(username);
    if (account != null && account.getPassword().equals(password)) {
      System.out.println("The account is valid");
      // code that logs the user into the application
    } else {
      incorrectLabel.setVisible(true);
      usernameInput.setUnFocusColor(Color.RED);
      passwordInput.setUnFocusColor(Color.RED);
      passwordInput.setText("");
    }
  }

  @FXML
  void switchToMainMenu(ActionEvent event) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  @FXML
  void switchToRegister(ActionEvent event) throws IOException {
    sceneController.switchScene("Register");
  }
}
