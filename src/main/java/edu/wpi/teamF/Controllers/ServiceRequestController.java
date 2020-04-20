package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ServiceRequestController implements Initializable {

  public ChoiceBox<String> choiceBoxLoc;
  public JFXTextField filterLoc;
  public TextArea textAreaDesc;
  public ChoiceBox<String> choiceBoxPriority;
  public ChoiceBox<String> choiceBoxType;
  public ServiceRequestFactory serviceRequest = ServiceRequestFactory.getFactory();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // add all location nodes in choiceboxLoc

    // priority chocies
    choiceBoxPriority.getItems().add("1");
    choiceBoxPriority.getItems().add("2");
    choiceBoxPriority.getItems().add("3");
    choiceBoxType.getItems().add("Security");
    choiceBoxType.getItems().add("Maintenance");

    // Location Options
    choiceBoxLoc.getItems().add("Intensive Care Unit");
    choiceBoxLoc.getItems().add("Hemo / Dialysis");
    choiceBoxLoc.getItems().add("Residents");
    choiceBoxLoc.getItems().add("Outpatient Infusion Center");
    choiceBoxLoc.getItems().add("Oncology");
    choiceBoxLoc.getItems().add("X-ray");
    choiceBoxLoc.getItems().add("Ambulatory Clinic");
    choiceBoxLoc.getItems().add("Ambulatory Care Room 1");
    choiceBoxLoc.getItems().add("Ambulatory Care Room 2");
    choiceBoxLoc.getItems().add("Ambulatory Care Room 3");
    choiceBoxLoc.getItems().add("Ambulatory Care Room 4");
    choiceBoxLoc.getItems().add("Internal Medicine");
    choiceBoxLoc.getItems().add("Suite 5940");
    choiceBoxLoc.getItems().add("Suite 5945");
    choiceBoxLoc.getItems().add("Suite 51");
    choiceBoxLoc.getItems().add("Suite 58");
    choiceBoxLoc.getItems().add("Ent / Eye Physical / Derm");
    choiceBoxLoc.getItems().add("Orthopedic");
    choiceBoxLoc.getItems().add("BWH Surgical Specialties");
    choiceBoxLoc.getItems().add("Endocrinology/ Diabetes/ Hem-Onc");
    choiceBoxLoc.getItems().add("Primary Care Physicians");
  }

  public void submit(ActionEvent actionEvent) {
    String ID = choiceBoxLoc.getValue();
    switch(ID){
      //case "Intensive Care Unit": serviceRequest.create();
    }
    System.out.println(ID);
    // serviceRequest.create();

  }

  public void clearTextFilter(MouseEvent mouseDragEvent) {
    filterLoc.setText("");
  }

  public void cancel(ActionEvent actionEvent) {}
}
