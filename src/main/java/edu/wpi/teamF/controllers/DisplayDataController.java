package edu.wpi.teamF.controllers;

import edu.wpi.teamF.factories.CSVManipulator;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class DisplayDataController extends SceneController {

  public AnchorPane rootPane;
  public TableView table;

  public void displayData(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select CSV File");
    File file = fileChooser.showOpenDialog(table.getScene().getWindow());
    String filepath = file.getAbsolutePath();

    CSVManipulator csvM = new CSVManipulator();
    csvM.readCSVFile(filepath);

    populateTable();
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

  private void populateTable() {}
}
