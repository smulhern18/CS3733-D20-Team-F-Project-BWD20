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
    sceneController.switchScene("DisplayData");
  }

  public void downloadSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void modifyValSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ModifyData");
  }

  public void test(ActionEvent actionEvent) {}
}
