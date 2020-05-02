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

  private MaintenanceRequestController maintenanceRequestController =
      new MaintenanceRequestController();

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {

    Scene primaryScene = new Scene(new AnchorPane());
    FXMLLoader fxmlLoader = new FXMLLoader();
    Parent root = FXMLLoader.load(getClass().getResource("Views/MaintenanceRequest.fxml"));
    primaryScene.setRoot(root);
    primaryStage.setScene(primaryScene);
    primaryStage.setAlwaysOnTop(true);
    primaryStage.show();
  }

  @Override
  public void stop() {}
}
