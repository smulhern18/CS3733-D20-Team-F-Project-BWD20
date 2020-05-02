package edu.wpi.teamF;

import edu.wpi.teamF.Controllers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static SceneController sceneController;

  public App() throws Exception {}

  public static SceneController getSceneController() {
    return sceneController;
  }
  private MainMenuController mainMenuController = new MainMenuController();
  private MenuBarController menuBarController = new MenuBarController();
  private MaintenanceRequestController maintenanceRequestController =
      new MaintenanceRequestController();

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {

    Scene primaryScene = new Scene(new AnchorPane());
    FXMLLoader fxmlLoader = new FXMLLoader();
    sceneController = new SceneController(fxmlLoader, primaryStage, primaryScene);
    Parent root = FXMLLoader.load(getClass().getResource("Views/MainMenu.fxml"));
    primaryScene.setRoot(root);
    primaryStage.setScene(primaryScene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
  }

  @Override
  public void stop() {}
}
