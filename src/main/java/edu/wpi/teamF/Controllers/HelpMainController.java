package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HelpMainController implements Initializable {

  public AnchorPane root;
  public Label textMenu;
  public ImageView background;
  public AnchorPane anchorPane;
  public Label welcomeLabel;
  public JFXButton loginButton;
  public JFXButton pathfinderButton;
  public JFXButton helpButton;
  public ImageView loginImage;
  public ImageView pathImage;
  public ImageView helpImage;
  public GridPane gridPane;

  SceneController sceneController = App.getSceneController();

  public void generalquestions(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Help");
  }

  public void specificquestions(ActionEvent actionEvent) throws IOException {
    // sceneController.switchScene("Help");
  }

  public void contact(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ContactUs");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //    resize(anchorPane.getWidth());
    anchorPane
        .widthProperty()
        .addListener(
            (observable, oldWidth, newWidth) -> {
              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
                //                resize(newWidth.doubleValue());
              }
            });
  }
}
