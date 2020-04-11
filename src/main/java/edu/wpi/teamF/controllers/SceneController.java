package edu.wpi.teamF.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

  public void switchScene(String aScene) throws IOException {
    Parent newFile =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/views/" + aScene + ".fxml"));
    Scene newScene = new Scene(newFile);
    Stage appStage = new Stage();
    appStage.setScene(newScene);
    appStage.show();
  }
}
