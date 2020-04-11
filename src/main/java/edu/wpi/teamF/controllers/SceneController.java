package edu.wpi.teamF.controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

  public void switchScene(ActionEvent actionEvent, String aScene) throws IOException {
    Parent newFile = FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/views/" + aScene + ".fxml"));
    Scene newScene = new Scene(newFile);
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    appStage.setScene(newScene);
    appStage.show();
  }
}
