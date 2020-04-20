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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

public class HelpController implements Initializable {

  @FXML public AnchorPane rootPane;
  @FXML private StackPane stackPane;

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void loadDialogsignup(ActionEvent actionEvent) {
    JFXDialogLayout content1 = new JFXDialogLayout();
    content1.setHeading(new Text("How to sign up?"));
    content1.setBody(
        new Text(
            "Click on the 'Login' button on the main menu. Then click on the 'Register'\n"
                + " button on Login page. After entering your information correctly, click on \n"
                + "'Register' button. The account will be created."));
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
  private void loadDialoglogin(ActionEvent actionEvent) {
    JFXDialogLayout content2 = new JFXDialogLayout();
    content2.setHeading(new Text("How to login?"));
    content2.setBody(
        new Text(
            "Click on the 'Login' button on Main Menu. You will go to the Login page. \n"
                + "If you have an account, enter the Username and Password. Then click \n"
                + "on the 'Login' button. If you don't have an account, then click on the\n"
                + " 'Register' button to sign up."));
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
  private void loadDialogservicerequest(ActionEvent actionEvent) {
    JFXDialogLayout content3 = new JFXDialogLayout();
    content3.setHeading(new Text("How to request a service?"));
    content3.setBody(
        new Text(
            "Click on the 'Service Request' button on Main Menu. You will go to the Service \n"
                + "Request page. You could request services for security and maintenance. If you \n"
                + "want to request service for security, click on 'Request Security'. If you want \n"
                + "to request service for maintenance, click on 'Request Maintenance'."));
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
  private void loadDialogrequestmain(ActionEvent actionEvent) {
    JFXDialogLayout content4 = new JFXDialogLayout();
    content4.setHeading(new Text("How to request maintenance service?"));
    content4.setBody(
        new Text(
            "Click on the 'Request Maintenance' button on Service Request page. Enter ID, \n"
                + "location and description of the service and choose its priority. Then click \n"
                + "on 'Submit'. If you want to cancel the request, click on 'Cancel'. "));
    JFXDialog dialog4 = new JFXDialog(stackPane, content4, JFXDialog.DialogTransition.CENTER);
    JFXButton button4 = new JFXButton("Close");
    button4.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            dialog4.close();
          }
        });
    content4.setActions(button4);
    dialog4.show();
  }

  @FXML
  private void loadDialogadmin(ActionEvent actionEvent) {
    JFXDialogLayout content5 = new JFXDialogLayout();
    content5.setHeading(new Text("How to add/edit/delete nodes?"));
    content5.setBody(
        new Text(
            "Click on the 'Admin' button on main menu. The information about nodes \n"
                + "and edges are shown in the table. If you want to view it in a map, click on \n"
                + "'Map View'. Click again on 'Map View' brings you back to table view. You \n"
                + "could quick search for nodes or edges by typing their ID in the text field.\n"
                + "If you want to upload nodes or edges, click on 'Upload Nodes' or 'Upload \n"
                + "Edges'. If you want to download nodes or edges, click on 'Download Nodes' \n"
                + "or 'Download Edges'. "));
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
  private void loadDialogpath(ActionEvent actionEvent) {
    JFXDialogLayout content6 = new JFXDialogLayout();
    content6.setHeading(new Text("How to find a path?"));
    content6.setBody(new Text("To be added later."));
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
}
