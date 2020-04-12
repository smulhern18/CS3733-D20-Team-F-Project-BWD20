package edu.wpi.teamF.controllers;


import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainMenuController extends SceneController {

  //Scene Controllers For the Main Menu

  SceneController sceneController = new SceneController();

  public void displayButton(javafx.event.ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DisplayData");
  }

  public void downloadButton(javafx.event.ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void modifyButton(javafx.event.ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ModifyValues");
  }

  public void pathfinderButton(javafx.event.ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }
}

