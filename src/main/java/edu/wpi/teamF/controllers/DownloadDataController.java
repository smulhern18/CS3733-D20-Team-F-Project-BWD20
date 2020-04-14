package edu.wpi.teamF.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;

public class DownloadDataController extends SceneController {
  SceneController sceneController = new SceneController();

  public void downloadData(ActionEvent actionEvent) {
    System.out.println("Test\n");
  }

  public void modifyValSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ModifyData");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void displaySwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DisplayData");
  }

  // Scene Controllers

}
