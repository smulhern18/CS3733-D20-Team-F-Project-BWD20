package edu.wpi.teamF.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MenuBarController {
  public Button mainMenuButton;
  public Button downloadButton;
  public Button pathFinderButton;
  public Button modifyValuesButton;
  public Button displayButton;
  SceneController sceneController = new SceneController();

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
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

  public void displaySwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DisplayData");
  }

  public void hideMainMenu() {}
}
