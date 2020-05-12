package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneController {

  private FXMLLoader loader;
  private Stage primaryStage;
  private Scene primaryScene;
  private Timeline timeline;
  private MenuBarController menuBarController;

  public SceneController(FXMLLoader fxmlLoader, Stage primaryStage, Scene primaryScene) {
    this.loader = fxmlLoader;
    this.primaryScene = primaryScene;
    this.primaryStage = primaryStage;
    this.menuBarController = new MenuBarController();
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                new Duration(15000),
                (evt) -> {
                  try {
                    menuBarController.logout(evt);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }));
    this.timeline = timeline;
    primaryStage.addEventFilter(MouseEvent.MOUSE_MOVED, (evt) -> timeline.playFromStart());
  }

  public void switchScene(String aScene) throws IOException {
    Parent root = loader.load(App.class.getResource("Views/" + aScene + ".fxml"));
    primaryScene.setRoot(root);
  }
}
