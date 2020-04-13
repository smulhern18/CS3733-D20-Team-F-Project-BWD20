package edu.wpi.teamF.controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController extends App {

  // Scene switching method
  App app = new App();

  public void switchScene(String aScene) throws IOException {
    Stage newstage = app.getPS();
    Parent root =
        FXMLLoader.load(getClass().getResource("/edu/wpi/teamF/views/" + aScene + ".fxml"));
    Scene scene = new Scene(root);
    newstage.setScene(scene);
    app.setPS(newstage);
    newstage.show();
  }
}
