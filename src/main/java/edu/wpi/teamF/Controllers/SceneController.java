package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

  private FXMLLoader loader;
  private Stage primaryStage;
  private Scene primaryScene;

  public SceneController(FXMLLoader fxmlLoader, Stage primaryStage, Scene primaryScene) {
    this.loader = fxmlLoader;
    this.primaryScene = primaryScene;
    this.primaryStage = primaryStage;
  }

  public void switchScene(String aScene) throws IOException {
    Parent root = loader.load(App.class.getResource("Views/" + aScene + ".fxml"));
    primaryScene.setRoot(root);
  }
}
