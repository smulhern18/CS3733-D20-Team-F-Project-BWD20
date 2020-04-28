package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainMenuController {

  public AnchorPane root;
  public Label textMenu;

  SceneController sceneController = App.getSceneController();

  public void login(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void pathfinder(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("PathfinderVersion2");
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

  public void login1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void serviceRequest1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void pathfinder1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void admin1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("DataManipulator");
  }

  public void help1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Help");
  }
}
