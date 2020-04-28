package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.event.ActionEvent;

public class MenuBarController {
  public JFXButton mainMenuButton;
  public JFXButton loginButton;
  public JFXButton serviceButton;
  public JFXButton pathfindButton;
  public JFXButton adminButton;
  public JFXButton helpButton;
  public JFXButton settingsButton;
  SceneController sceneController = App.getSceneController();

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
    sceneController.switchScene("DataManipulator");
  }

  public void help(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Help");
  }

  public void settings(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Accounts");
  }

  public void mainMenu(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenuVersion2");
  }
}
