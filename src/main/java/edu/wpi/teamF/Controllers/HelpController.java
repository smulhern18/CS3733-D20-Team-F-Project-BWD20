package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class HelpController implements Initializable {

  @FXML public AnchorPane rootPane;
  @FXML private StackPane stackPane;
  public ImageView background;
  // @FXML
  // private JFXButton textfield;
  // @FXML
  // private JFXButton search;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    background.setPreserveRatio(false);
    background.fitHeightProperty().bind(rootPane.heightProperty());
    background.fitWidthProperty().bind(rootPane.widthProperty());
  }

  @FXML
  private void signup(ActionEvent actionEvent) {
    JFXDialogLayout content1 = new JFXDialogLayout();
    content1.setHeading(new Text("How to sign up?"));
    content1.setBody(
        new Text(
            "1. Click on the 'Login' button on the main menu. \n2. Click on 'Register'. \n3. Enter your information.\n4. Click on 'Register'. New account will be created."));
    JFXDialog dialog1 = new JFXDialog(stackPane, content1, JFXDialog.DialogTransition.CENTER);
    JFXButton button1 = new JFXButton("Close");
    button1.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog1.close();
          }
        });
    content1.setActions(button1);
    dialog1.show();
  }

  @FXML
  private void login(ActionEvent actionEvent) {
    JFXDialogLayout content2 = new JFXDialogLayout();
    content2.setHeading(new Text("How to login?"));
    content2.setBody(
        new Text(
            "1. Click on the 'Login' button on Main Menu.\n"
                + "2. If you have an account, then login.\n"
                + "3. If you don't have an account, then click on 'Register' to sign up.\n"));
    JFXDialog dialog2 = new JFXDialog(stackPane, content2, JFXDialog.DialogTransition.CENTER);
    JFXButton button2 = new JFXButton("Close");
    button2.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog2.close();
          }
        });
    content2.setActions(button2);
    dialog2.show();
  }

  @FXML
  private void findpath(ActionEvent actionEvent) {
    JFXDialogLayout content3 = new JFXDialogLayout();
    content3.setHeading(new Text("How to find a path?"));
    content3.setBody(
        new Text(
            "1.\tGo to pathfinder page.\n"
                + "2.\tSelect or search current location from drop down menu.\n"
                + "3.\tYou can also click current location directly from the map. \n"
                + "4.\tSelect the destination. \n"
                + "5.\tSelect a preference (elevators or stairs).\n"
                + "6.\tClick Find path. \n"
                + "7.\tClick Reset to find another shortest path.\n"));
    JFXDialog dialog3 = new JFXDialog(stackPane, content3, JFXDialog.DialogTransition.CENTER);
    JFXButton button3 = new JFXButton("Close");
    button3.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog3.close();
          }
        });
    content3.setActions(button3);
    dialog3.show();
  }

  @FXML
  private void findnear(ActionEvent actionEvent) {
    JFXDialogLayout content5 = new JFXDialogLayout();
    content5.setHeading(new Text("How to find the nearest restrooms, elevators or stairs?"));
    content5.setBody(
        new Text(
            "1.\tGo to pathfinder page. \n"
                + "2.\tSelect or search current location from drop down menu.\n"
                + "3.\tYou can also click current location directly from the map. \n"
                + "4.\tClick on the icon of restrooms, elevators or stairs.\n"
                + "5.\tClick Find Path. \n"
                + "6.\tClick Reset to find another shortest path.\n"));
    JFXDialog dialog5 = new JFXDialog(stackPane, content5, JFXDialog.DialogTransition.CENTER);
    JFXButton button5 = new JFXButton("Close");
    button5.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog5.close();
          }
        });
    content5.setActions(button5);
    dialog5.show();
  }

  @FXML
  private void servicerequest(ActionEvent actionEvent) {
    JFXDialogLayout content6 = new JFXDialogLayout();
    content6.setHeading(new Text("How to request a service?"));
    content6.setBody(
        new Text(
            "1.\tGo to Service Request page.\n"
                + "2.\tSelect a service you need.\n"
                + "3.\tEnter relevant information.\n"
                + "4.\tClick Submit. The request is sent.\n"));
    JFXDialog dialog6 = new JFXDialog(stackPane, content6, JFXDialog.DialogTransition.CENTER);
    JFXButton button6 = new JFXButton("Close");
    button6.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog6.close();
          }
        });
    content6.setActions(button6);
    dialog6.show();
  }

  @FXML
  private void findatt(ActionEvent actionEvent) {
    JFXDialogLayout content7 = new JFXDialogLayout();
    content7.setHeading(new Text("How to find attractions or restaurants?"));
    content7.setBody(
        new Text(
            "1.\tGo to Tools page.\n"
                + "2.\tClick on Find Attractions.\n"
                + "3.\tEnter attractions or restaurants you want to search.\n"
                + "4.\tSelect where you are (Main Campus or Faulkner Hospital).\n"
                + "5.\tClick Search.\n"
                + "6.\tClick on a search result.\n"
                + "7.\tGoogle map jumps out to show you the way to get there. \n"
                + "8.\tIf you enter your number, directions of text will be sent to mobile. \n"));
    JFXDialog dialog7 = new JFXDialog(stackPane, content7, JFXDialog.DialogTransition.CENTER);
    JFXButton button7 = new JFXButton("Close");
    button7.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog7.close();
          }
        });
    content7.setActions(button7);
    dialog7.show();
  }

  @FXML
  private void news(ActionEvent actionEvent) {
    JFXDialogLayout content8 = new JFXDialogLayout();
    content8.setHeading(new Text("How to find news articles?"));
    content8.setBody(
        new Text(
            "1.\tGo to Tools page.\n"
                + "2.\tSelect a interested news article.\n"
                + "3.\tClick on View Articles to read.\n"
                + "4.\tThe articles can be sent to mobile if you want.\n"
                + "5.\tNews can be searched.  \n"));
    JFXDialog dialog8 = new JFXDialog(stackPane, content8, JFXDialog.DialogTransition.CENTER);
    JFXButton button8 = new JFXButton("Close");
    button8.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog8.close();
          }
        });
    content8.setActions(button8);
    dialog8.show();
  }

  @FXML
  private void translate(ActionEvent actionEvent) {
    JFXDialogLayout content9 = new JFXDialogLayout();
    content9.setHeading(new Text("How to use a translator?"));
    content9.setBody(
        new Text(
            "1.\tGo to Tools page.\n"
                + "2.\tSelect languages of translate from and translate to. \n"
                + "3.\tEnter text to translate. Click on Translate.\n"
                + "4.\tTranslated text can be sent to mobile.\n"
                + "5.\tTranslated text can be called to mobile.\n"));
    JFXDialog dialog9 = new JFXDialog(stackPane, content9, JFXDialog.DialogTransition.CENTER);
    JFXButton button9 = new JFXButton("Close");
    button9.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog9.close();
          }
        });
    content9.setActions(button9);
    dialog9.show();
  }
}
