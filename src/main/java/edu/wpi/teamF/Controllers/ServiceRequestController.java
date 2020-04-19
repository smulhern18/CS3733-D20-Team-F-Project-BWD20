package edu.wpi.teamF.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

public class ServiceRequestController implements Initializable {

  public ChoiceBox<String> choiceBox;
  public Text confirmationSecurity;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    choiceBox.getItems().add("1");
    choiceBox.getItems().add("2");
    choiceBox.getItems().add("3");
  }

  public void submit(ActionEvent actionEvent) {}

  public void reqSecurity(ActionEvent actionEvent) {
    confirmationSecurity.setVisible(true);
  }
}
