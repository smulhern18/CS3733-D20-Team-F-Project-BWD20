package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.File;

import edu.wpi.teamF.Factories.CSVManipulator;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

public class DownloadDataController extends SceneController {

  public void downloadData(ActionEvent actionEvent) {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Select Which Directory To Save Your File");
    File selectedDirectory = directoryChooser.showDialog(App.getPS());
    String directory = selectedDirectory.getAbsolutePath();
    System.out.println(directory);

    CSVManipulator downloader = new CSVManipulator();
    downloader.writeCSVFile();



  }
}
