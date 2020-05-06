package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.shape.Line;

public class MenuBarController implements Initializable {
  public JFXButton mainMenuButton;
  public JFXButton loginButton;
  public JFXButton serviceButton;
  public JFXButton pathfindButton;
  public JFXButton adminButton;
  public JFXButton helpButton;
  public JFXButton settingsButton;
  public Line line6;
  public JFXButton adminButton1;
  public JFXButton settingsButton1;
  public JFXButton helpButton1;
  public JFXButton pathfindButton1;
  SceneController sceneController = App.getSceneController();
  DatabaseManager dbm = DatabaseManager.getManager();

  public void login(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void pathfinder(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void serviceRequest(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void admin(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DataMapView");
  }

  public void help(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("HelpMain");
  }

  public void settings(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void mainMenu(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Account.Type userLevel = dbm.getPermissions();
    if (userLevel == null || userLevel == Account.Type.USER) {
      adminButton.setVisible(false);
      adminButton.setDisable(true);
      adminButton1.setVisible(false);
      adminButton1.setDisable(true);
      line6.setVisible(false);
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      adminButton.setDisable(false);
      adminButton.setVisible(true);
      adminButton1.setVisible(true);
      adminButton1.setDisable(false);
      line6.setVisible(true);
      // set to staff
      // enable admin page
    }
  }

  // logout method

}
