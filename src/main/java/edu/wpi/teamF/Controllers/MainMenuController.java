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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MainMenuController implements Initializable {

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

  public void login(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void pathfinder(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void serviceRequest(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void admin(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("DataManipulator");
  }

  public void help(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("HelpMain");
  }

  public void login1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Login");
  }

  public void serviceRequest1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void pathfinder1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Pathfinder");
  }

  public void admin1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("DataManipulator");
  }

  public void help1(MouseEvent mouseEvent) throws IOException {
    sceneController.switchScene("Help");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background.fitWidthProperty().bind(anchorPane.widthProperty());
    background.fitHeightProperty().bind(anchorPane.heightProperty());
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

  //    private void resize(double width) {
  //      System.out.println(width);
  //      Font newFont = new Font(width / 50);
  //      loginImage.setFitWidth(gridPane.getCellBounds(0, 1).getWidth());
  //      loginImage.setFitHeight(gridPane.getCellBounds(0, 1).getHeight());
  //      pathImage.setFitWidth(gridPane.getCellBounds(0, 2).getWidth());
  //      pathImage.setFitHeight(gridPane.getCellBounds(0, 2).getHeight());
  //      helpImage.setFitHeight(gridPane.getCellBounds(0, 3).getWidth());
  //      helpImage.setFitWidth(gridPane.getCellBounds(0, 3).getHeight());
  //      welcomeLabel.setFont(newFont);
  //    }
}
