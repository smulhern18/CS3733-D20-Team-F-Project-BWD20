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
  private void loadDialog1(ActionEvent actionEvent) {
    JFXDialogLayout content1 = new JFXDialogLayout();
    content1.setHeading(new Text("How to sign up?"));
    content1.setBody(new Text("Enter the information on the sign up page."));
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
}
