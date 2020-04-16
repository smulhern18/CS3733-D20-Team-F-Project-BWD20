package edu.wpi.teamF;

import edu.wpi.teamF.Factories.CSVManipulator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws IOException {
    try {
      CSVManipulator csvM = new CSVManipulator();
      //csvM.readCSVFileNode(Paths.get(getClass().getResource("csv/MapFnodes.csv").toURI()));
      //csvM.readCSVFileEdge(Paths.get(getClass().getResource("csv/MapFedges.csv").toURI()));
      Parent root = FXMLLoader.load(getClass().getResource("views/MainMenu.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      PS = primaryStage;
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
      Platform.exit();
    }
  }

  @Override
  public void stop() {}
}
