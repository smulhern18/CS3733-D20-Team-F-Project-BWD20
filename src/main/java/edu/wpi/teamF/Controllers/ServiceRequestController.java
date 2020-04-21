package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestFactory;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import javax.management.InstanceNotFoundException;

public class ServiceRequestController implements Initializable {

  public ChoiceBox<String> choiceBoxLoc;
  public JFXTextField filterLoc;
  public TextArea textAreaDesc;
  public ChoiceBox<String> choiceBoxPriority;
  public ChoiceBox<String> choiceBoxType;
  public ServiceRequestFactory serviceRequest = ServiceRequestFactory.getFactory();
  public NodeFactory nodeFactory = NodeFactory.getFactory();
  public SecurityRequestFactory securityRequestFactory =  SecurityRequestFactory.getFactory();
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

  public void submit(ActionEvent actionEvent) throws InstanceNotFoundException, ValidationException {
    int priority = Integer.parseInt(choiceBoxPriority.getValue());
    String locationName = choiceBoxLoc.getValue();
    String nodeID = null;
    if (locationName.equals("Intensive Care Unit")) {
      nodeID = "FDEPT00105";
    }
    else if(locationName.equals("Hemo / Dialysis")) {
      nodeID = "FDEPT00205";
    }
    else if(locationName.equals("Residents")) {
      nodeID = "FDEPT00405";
    }
    else if(locationName.equals("Outpatient Infusion Center")) {
      nodeID = "FDEPT00305";
    }
    else if(locationName.equals("Oncology")) {
      nodeID = "FDEPT00505";
    }
    else if(locationName.equals("X-ray")) {
      nodeID = "FDEPT00605";
    }
    else if(locationName.equals("Ambulatory Clinic")) {
      nodeID = "FDEPT01305";
    }
    else if(locationName.equals("Ambulatory Care Room 1")) {
      nodeID = "FDEPT00705";
    }
    else if(locationName.equals("Ambulatory Care Room 2")) {
      nodeID = "FDEPT00905";
    }
    else if(locationName.equals("Ambulatory Care Room 3")) {
      nodeID = "FDEPT01005";
    }
    else if(locationName.equals("Ambulatory Care Room 4")) {
      nodeID = "FDEPT01105";
    }
    else if(locationName.equals("Internal Medicine")) {
      nodeID = "FDEPT01405";
    }
    else if(locationName.equals("Suite 5940")) {
      nodeID = "FSERV00305";
    }
    else if(locationName.equals("Suite 5945")) {
      nodeID = "FSERV00205";
    }
    else if(locationName.equals("Suite 51")) {
      nodeID = "FSERV00405";
    }
    else if(locationName.equals("Suite 58")) {
      nodeID = "FSERV00505";
    }
    else if(locationName.equals("Ent / Eye Physical / Derm")) {
      nodeID = "FDEPT01605";
    }
    else if(locationName.equals("Orthopedic")) {
      nodeID = "FDEPT01705";
    }
    else if(locationName.equals("BWH Surgical Specialties")) {
      nodeID = "FDEPT01805";
    }
    else if(locationName.equals("Endocrinology/ Diabetes/ Hem-Onc")) {
      nodeID = "FDEPT01505";
    }
    else if(locationName.equals("Primary Care Physicians")) {
      nodeID = "FDEPT00805";
    }



    String serviceType = choiceBoxType.getValue();
    Date date = new Date(System.currentTimeMillis());
    String id = nodeID + date;
    if (serviceType.equals("Security")) {
      SecurityRequest secRequest = new SecurityRequest(id, nodeFactory.read(nodeID), textAreaDesc.getText(), date, priority);

    } else if (serviceType.equals("Maintenance")) {
      MaintenanceRequest maintenanceRequest = new MaintenanceRequest(id, nodeFactory.read(nodeID), textAreaDesc.getText(), date, priority);
    }

  }

  public void clearTextFilter(MouseEvent mouseDragEvent) {
    filterLoc.setText("");
  }

  public void cancel(ActionEvent actionEvent) {}
}
