package edu.wpi.teamF.Controllers;

import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpMainController implements Initializable {

  public AnchorPane root;
  public ImageView background;
  public AnchorPane anchorPane;
  public Label adminLabel;
  public Label generalLabel;
  public AnchorPane frame;
  public ImageView backgroundImage;
  public ImageView questions;
  public ImageView adminImage;
  public ImageView contactUs;
  public ImageView generalImage;

  SceneController sceneController = App.getSceneController();
  DatabaseManager dbm = DatabaseManager.getManager();

  public void generalquestions(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Help");
  }

  public void language(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("HelpAdmin");
  }

  public void contact(MouseEvent mouseEvent) throws IOException {
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
    backgroundImage.setPreserveRatio(false);
    backgroundImage.fitHeightProperty().bind(frame.heightProperty());
    backgroundImage.fitWidthProperty().bind(frame.widthProperty());

    Account.Type userLevel = dbm.getPermissions();
    if (userLevel == null) {
      // disable admin
      adminImage.setDisable(true);
      adminImage.setVisible(false);
      adminLabel.setVisible(false);
    } else if (userLevel == Account.Type.USER) {
      // Enable admin
      adminImage.setDisable(true);
      adminImage.setVisible(false);
      adminLabel.setVisible(false);
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      // Enable admin
      adminImage.setDisable(false);
      adminImage.setVisible(true);
      adminLabel.setVisible(true);
      // disable admin
      generalImage.setDisable(true);
      generalImage.setVisible(false);
      generalLabel.setVisible(false);
    }
  }
}
