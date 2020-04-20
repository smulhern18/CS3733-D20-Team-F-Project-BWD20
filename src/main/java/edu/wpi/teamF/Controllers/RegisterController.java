package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.AccountFactory;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class RegisterController {

  @FXML private Label incorrectLabel; //used to display errors

  @FXML private JFXTextField firstNameInput;

  @FXML private JFXTextField lastNameInput;

  @FXML private JFXTextField emailInput;

  @FXML private JFXTextField usernameInput;

  @FXML private JFXPasswordField passwordInput;

  @FXML private JFXButton registerButton;

  AccountFactory accountFactory = AccountFactory.getFactory();
  SceneController sceneController = App.getSceneController();

  @FXML
  void attemptRegister(ActionEvent event) throws Exception {
    String firstName = firstNameInput.getText();
    String lastName = lastNameInput.getText();
    String email = emailInput.getText();
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    Account account = accountFactory.read(username);

    if (account == null && password.length() >= 8 && email.contains("@")) { //The account is valid
      System.out.println("The account is valid");
      Account newAccount = new User(firstName, lastName, email, username, password);
      accountFactory.create(newAccount); //creates an account with the input
      switchToLogin(event);
    } else if (!email.contains("@")) { //The email is not valid (no "@" symbol)
      incorrectLabel.setVisible(true);
      incorrectLabel.setText("The email is not valid");
      emailInput.setUnFocusColor(Color.RED);
    } else if (account != null) { //The username already exists
      incorrectLabel.setVisible(true);
      incorrectLabel.setText("The username already exists");
      usernameInput.setUnFocusColor(Color.RED);
    } else { //The password entered contains less than 8 characters
      incorrectLabel.setVisible(true);
      incorrectLabel.setText("The password needs to contain atleast 8 characters");
      passwordInput.setUnFocusColor(Color.RED); //highlights the password field red
      passwordInput.setText("");
    }
  }

  @FXML
  public void enableRegister(KeyEvent keyEvent) { //called on each key release for all of the input fields
    String firstName = firstNameInput.getText();
    String lastName = lastNameInput.getText();
    String email = emailInput.getText();
    String username = usernameInput.getText();
    String password = passwordInput.getText();

    if (!firstName.isEmpty()
        && !lastName.isEmpty()
        && !email.isEmpty()
        && !username.isEmpty()
        && !password.isEmpty()) { //every field needs to be populated to enable the register button
      registerButton.setDisable(false);
    } else {
      registerButton.setDisable(true);
    }
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    sceneController.switchScene("Login");
  }

  @FXML
  void switchToMainMenu(ActionEvent event) throws IOException {
    sceneController.switchScene("MainMenu");
  }
}
