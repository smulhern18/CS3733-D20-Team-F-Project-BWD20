package edu.wpi.teamF.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainMenuController {

  @FXML private AnchorPane root;

  SceneController sceneController = new SceneController();

  //  @Override
  //  public void initialize(URL location, ResourceBundle resources) {
  //    try {
  //      // load splash screen view
  //      StackPane pane =
  // FXMLLoader.load(getClass().getResource(("edu/wpi/teamF/Views/Splash.fxml")));
  //      // add to root container
  //      root.getChildren().setAll(pane);
  //
  //      // load splash screen with fade in
  //      FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
  //      fadeIn.setFromValue(0);
  //      fadeIn.setToValue(1);
  //      fadeIn.setCycleCount(1);
  //
  //      // fade out
  //      FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
  //      fadeOut.setFromValue(1);
  //      fadeOut.setToValue(0);
  //      fadeOut.setCycleCount(1);
  //
  //      fadeIn.play();
  //
  //      // after fade in, fade out
  //      fadeIn.setOnFinished(
  //          (e) -> {
  //            fadeOut.play();
  //          });
  //
  //      // after fade out, show main menu
  //      fadeOut.setOnFinished(
  //          (e) -> {
  //            try {
  //              AnchorPane main =
  //
  // FXMLLoader.load(getClass().getResource(("edu/wpi/teamF/Views/MainMenu.fxml")));
  //              root.getChildren().setAll(main);
  //            } catch (IOException ex) {
  //              Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
  //            }
  //          });
  //
  //    } catch (IOException ex) {
  //      Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
  //    }
  //  }
  @FXML
  void switchToPathfinder(ActionEvent event) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void serviceRequest(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequest");
  }

  public void admin(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DataManipulator");
  }

  public void help(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Help");
  }

  public void login(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Login");
  }
}
