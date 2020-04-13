package edu.wpi.teamF.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainMenuController extends SceneController {
  @FXML AnchorPane rootPane;

  // Scene Controllers For the Main Menu

  SceneController sceneController = new SceneController();

  public void downloadButton(ActionEvent actionEvent) {}

  public void modifyButton(ActionEvent actionEvent) {}

  public void pathfinderButton(ActionEvent actionEvent) {}

  public void displaySwitch(ActionEvent actionEvent) throws IOException {
    System.out.println("Test");
    sceneController.switchScene("Display");
  }

  public void downloadSwitch(ActionEvent actionEvent) {}

  public void pathfinderSwitch(ActionEvent actionEvent) {}

  public void modifyValSwitch(ActionEvent actionEvent) {}

  public void test(ActionEvent actionEvent) {}
}
