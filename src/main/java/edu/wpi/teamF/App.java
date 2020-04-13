package edu.wpi.teamF;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  private static Stage PS;

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("views/MainMenu.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    PS = primaryStage;
    primaryStage.show();
  }

  @Override
  public void stop() {}

  public Stage getPS() {
    return PS;
  }

  public Stage setPS(Stage PS) {
    return App.PS = PS;
  }
}
