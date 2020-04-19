package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

public class ServiceRequestController implements Initializable {

  public ChoiceBox<String> choiceBox;
  public Text confirmationSecurity;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public JFXTextField idText;
  public JFXTextField locText;
  public JFXTextArea descText;
  public ChoiceBox<String> priorityChoice;
  public Text priority;
  public Text locationtxt;
  public Text text;
  public Text description;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    submitButton.setVisible(false);
    cancelButton.setVisible(false);
    idText.setVisible(false);
    locText.setVisible(false);
    descText.setVisible(false);
    priorityChoice.setVisible(false);
    priority.setVisible(false);
    locationtxt.setVisible(false);
    text.setVisible(false);
    description.setVisible(false);
    priorityChoice.getItems().add("1");
    priorityChoice.getItems().add("2");
    priorityChoice.getItems().add("3");
  }

  public void reqSecurity(ActionEvent actionEvent) {
    confirmationSecurity.setVisible(true);
  }

  public void submit(ActionEvent actionEvent) {

    // create new service request for DB
  }

  public void cancel(ActionEvent actionEvent) {
    // clear text fields
  }

  public void reqMain(ActionEvent actionEvent) {
    submitButton.setVisible(true);
    cancelButton.setVisible(true);
    idText.setVisible(true);
    locText.setVisible(true);
    descText.setVisible(true);
    priorityChoice.setVisible(true);
    priority.setVisible(true);
    locationtxt.setVisible(true);
    text.setVisible(true);
    description.setVisible(true);
  }
}
