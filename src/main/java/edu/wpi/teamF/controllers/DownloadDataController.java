package edu.wpi.teamF.controllers;

import edu.wpi.teamF.App;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

public class DownloadDataController extends SceneController {

  public void downloadData(ActionEvent actionEvent) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Select Which Directory To Save Your File");
    File selectedDirectory = directoryChooser.showDialog(App.getPS());
    String directory = selectedDirectory.getAbsolutePath();
    System.out.println(directory);

    saveFile();
  }

  public void saveFile() {}
}
