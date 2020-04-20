package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class LoginController {

  @FXML private JFXButton loginButton;

  @FXML private JFXTextField usernameInput;

  @FXML private JFXPasswordField passwordInput;

  @FXML private JFXButton registerButton;

  @FXML private Label incorrectLabel;

  private AccountFactory accountFactory = AccountFactory.getFactory();

  @FXML
  void enableLogin(KeyEvent event) {
    //    String username = usernameInput.getText();
    //    String password = passwordInput.getText();
    //    if (!username.isEmpty() && !password.isEmpty()) {
    //      loginButton.setDisable(false);
    //    } else {
    //      loginButton.setDisable(true);
    //    }
  }

  @FXML
  void attemptLogin(ActionEvent event) throws InterruptedException {
    //    String username = usernameInput.getText();
    //    String password = passwordInput.getText();
    //    Account account = accountFactory.getAccountByUsername(username);
    //    if (account != null && account.getPassword().equals(password)) {
    //      System.out.println("The account is valid");
    //    } else {
    //      incorrectLabel.setVisible(true);
    //      usernameInput.setUnFocusColor(Color.RED);
    //      passwordInput.setUnFocusColor(Color.RED);
    //      passwordInput.setText("");
    //    }
  }

  @FXML
  void switchToMainMenu(ActionEvent event) {}

  @FXML
  void switchToRegister(ActionEvent event) {}
}
