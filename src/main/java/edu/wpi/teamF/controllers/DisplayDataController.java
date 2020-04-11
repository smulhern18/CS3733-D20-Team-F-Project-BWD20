package edu.wpi.teamF.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DisplayDataController extends SceneController {

  public void switchToMain(ActionEvent actionEvent) throws IOException {
    Parent mainMenu = FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/views/MainMenu.fxml"));
    Scene mainMenuScene = new Scene(mainMenu);
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

    appStage.setScene(mainMenuScene);
    appStage.show();
  }
}
