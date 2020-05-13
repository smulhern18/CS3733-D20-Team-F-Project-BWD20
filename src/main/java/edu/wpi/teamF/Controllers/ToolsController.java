package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ToolsController implements Initializable {
  public Label serviceRequestLabel;
  public GridPane gridPaneAll;
  public ImageView handPic;
  public JFXButton sanText;
  public ImageView secHEad;
  public JFXButton secText;
  public GridPane gridUser;

  // Images
  public ImageView attractionsImage;
  public ImageView newsImage;
  public ImageView translatorImage;
  public ImageView shippingImage;
  public ImageView backgroundImage;
  public AnchorPane anchorPane;
  // Text labels
  public Label shippingLabel;

  DatabaseManager dbm = DatabaseManager.getManager();
  SceneController sceneController = App.getSceneController();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());

    Account.Type userLevel = dbm.getPermissions();
    if (userLevel == null) {
      // disable shipping
      shippingImage.setDisable(true);
      shippingImage.setVisible(false);
      shippingLabel.setVisible(false);
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      // Enable shipping
      shippingImage.setDisable(false);
      shippingImage.setVisible(true);
      shippingLabel.setVisible(true);
    }
    // do new permissions
    // non-logged in have only access to sanitation and security requests

    // logged in of type USER can request everything but Transport and Maintenance

    // Staff and Admin can request all
  }

  public void shippingBtn(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Shipping");
  }

  public void newsBtn(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("News");
  }

  public void translatorBtn(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Translator");
  }

  public void attractionsBtn(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Attractions");
  }
}
