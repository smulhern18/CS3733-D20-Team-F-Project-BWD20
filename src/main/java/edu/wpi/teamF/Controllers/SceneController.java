package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneController implements PropertyChangeListener {

  private FXMLLoader loader;
  private Stage primaryStage;
  private Scene primaryScene;
  private Timeline timeline;
  private MenuBarController menuBarController;
  private AccountsController accountsController;

  public SceneController(FXMLLoader fxmlLoader, Stage primaryStage, Scene primaryScene) {
    this.loader = fxmlLoader;
    this.primaryScene = primaryScene;
    this.primaryStage = primaryStage;
    this.menuBarController = new MenuBarController();
    Timeline timeline =
        new Timeline(
            new KeyFrame(
                new Duration(150000),
                (evt) -> {
                  menuBarController.autoLogout();
                }));
    this.timeline = timeline;
    primaryStage.addEventFilter(MouseEvent.MOUSE_MOVED, (evt) -> timeline.playFromStart());
  }

  public void switchScene(String aScene) throws IOException {
    Parent root = loader.load(App.class.getResource("Views/" + aScene + ".fxml"));
    primaryScene.setRoot(root);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    this.timeline =
        new Timeline(
            new KeyFrame(
                new Duration((double) evt.getNewValue()),
                (evt2) -> {
                  menuBarController.autoLogout();
                }));
  }
}
