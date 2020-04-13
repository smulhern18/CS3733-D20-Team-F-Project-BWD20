package edu.wpi.teamF.controllers;

import edu.wpi.teamF.App;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

public class DisplayDataController extends SceneController {

  @

  public void displayData(ActionEvent actionEvent) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File("src"));

    File selectedDirectory = directoryChooser.showDialog(App.getPS());
    displayData();
  }

  SceneController sceneController = new SceneController();

  public void modifyValSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ModifyData");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void downloadSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void displayData() {}
}
