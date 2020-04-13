package edu.wpi.teamF.controllers;

import java.awt.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModifyDataController extends SceneController {
  SceneController sceneController = new SceneController();

  public void mainMenuSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void pathfinderSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("MainMenu");
  }

  public void downloadSwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DownloadData");
  }

  public void displaySwitch(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DisplayData");
  }

  @FXML private TextField nodeText;
  @FXML private Button addButton;
  @FXML private Button deleteButton;
  @FXML private Button editButton;

  @FXML
  private void validateButton() {
    if (!nodeText.getText().isEmpty()) {
      addButton.setDisable(false);
      deleteButton.setDisable(false);
      editButton.setDisable(false);
    }
  }

  public void addNode(ActionEvent actionEvent) {}

  public void deleteNode(ActionEvent actionEvent) {}

  public void editNode(ActionEvent actionEvent) {}
}
