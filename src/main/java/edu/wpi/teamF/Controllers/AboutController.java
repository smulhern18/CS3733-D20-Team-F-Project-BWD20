package edu.wpi.teamF.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AboutController implements Initializable {
  public AnchorPane frame;
  public ImageView backgroundImage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //    resize(anchorPane.getWidth());
    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());
  }
}
