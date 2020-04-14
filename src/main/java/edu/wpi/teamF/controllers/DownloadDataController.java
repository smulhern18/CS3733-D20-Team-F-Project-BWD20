package edu.wpi.teamF.controllers;

import edu.wpi.teamF.App;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

public class DownloadDataController extends SceneController {
  SceneController sceneController = new SceneController();

  public void downloadData(ActionEvent actionEvent) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Select Which Directory To Save Your File");
    File selectedDirectory = directoryChooser.showDialog(App.getPS());
    String directory = selectedDirectory.getAbsolutePath();
    System.out.println(directory);

    saveFile();
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

  public void saveFile() {}
}
