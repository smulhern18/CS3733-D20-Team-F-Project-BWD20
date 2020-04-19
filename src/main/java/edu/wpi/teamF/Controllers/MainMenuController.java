package edu.wpi.teamF.Controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MainMenuController {

  @FXML private AnchorPane root;

  private void loadSplashScreen() {
    try {
      // load splash screen view
      StackPane pane = FXMLLoader.load(getClass().getResource(("Splash.fxml")));
      // add to root container
      root.getChildren().setAll(pane);

      // load splash screen with fade in
      FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
      fadeIn.setFromValue(0);
      fadeIn.setToValue(1);
      fadeIn.setCycleCount(1);

      // fade out
      FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
      fadeOut.setFromValue(1);
      fadeOut.setToValue(0);
      fadeOut.setCycleCount(1);

      fadeIn.play();

      // after fade in, fade out
      fadeIn.setOnFinished(
          (e) -> {
            fadeOut.play();
          });

      // after fade out, show main menu
      fadeOut.setOnFinished(
          (e) -> {
            try {
              AnchorPane main = FXMLLoader.load(getClass().getResource(("MainMenu.fxml")));
              root.getChildren().setAll(main);
            } catch (IOException ex) {
              Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
          });

    } catch (IOException ex) {
      Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
