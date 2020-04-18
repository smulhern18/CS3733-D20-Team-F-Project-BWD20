package edu.wpi.teamF;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  private static Stage PS;

  protected static Stage getPS() {
    return PS;
  }

  public static void setPS(Stage stage) {
    App.PS = stage;
  }

  @Override
  public void init() {
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("views/MainMenu.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      PS = primaryStage;
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
      Platform.exit();
    }
  }

  @Override
  public void stop() {}
}
