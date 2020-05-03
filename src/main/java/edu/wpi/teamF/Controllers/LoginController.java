package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginController implements Initializable {

  public Label loginText;
  public AnchorPane anchorPane;
  public JFXButton registerButton;
  public Label orText;
  public Label forgotText;
  @FXML private JFXButton loginButton;

  @FXML private JFXTextField usernameInput;

  @FXML private JFXPasswordField passwordInput;

  @FXML private Label incorrectLabel; // label that is displayed if teh input is not valid
  @FXML private ImageView background;
  @FXML private StackPane stackPane;

  SceneController sceneController = App.getSceneController(); // used to switch between scenes

  DatabaseManager databaseManager = DatabaseManager.getManager();

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
      if (databaseManager.verifyPassword(username, password)) { // does the password match
        System.out.println("The account is valid");
        // set account in db to be the logged in one

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
  void switchToMainMenu2() throws IOException {
    sceneController.switchScene("MainMenu");
  }

  @FXML
  void switchToRegister(ActionEvent event) throws IOException {
    sceneController.switchScene("Register");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchorPane
        .widthProperty()
        .addListener(
            (observable, oldWidth, newWidth) -> {
              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
                resize(newWidth.doubleValue());
              }
            });
    background.fitHeightProperty().bind(stackPane.heightProperty());
    background.fitWidthProperty().bind(stackPane.widthProperty());
  }

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    // deleteButton.setFont(new Font(width / 50));
  }
}
