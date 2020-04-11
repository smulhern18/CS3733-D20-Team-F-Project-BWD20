package edu.wpi.teamF.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainMenuController extends SceneController {

  SceneController sceneController = new SceneController();

  public void switchToDisplayData(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene(actionEvent, "DisplayData");
  }

  public void switchToDownloadData(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene(actionEvent, "DownloadData");
  }

  public void switchToModifyValues(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene(actionEvent, "ModifyValues");
  }

  public void switchToPathfinder(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene(actionEvent, "Pathfinder");
  }
}
