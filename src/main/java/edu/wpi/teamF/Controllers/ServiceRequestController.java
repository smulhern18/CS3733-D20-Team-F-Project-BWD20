package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.MaintenanceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.DatabaseManipulators.SecurityRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.ServiceRequestFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.UIServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class ServiceRequestController implements Initializable {

  public ChoiceBox<String> choiceBoxLoc;
  public JFXTextField filterLoc;
  public TextArea textAreaDesc;
  public ChoiceBox<String> choiceBoxPriority;
  public ChoiceBox<String> choiceBoxType;
  public ServiceRequestFactory serviceRequest = ServiceRequestFactory.getFactory();
  public NodeFactory nodeFactory = NodeFactory.getFactory();
  public SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  public MaintenanceRequestFactory maintenanceRequestFactory =
      MaintenanceRequestFactory.getFactory();
  public ObservableList<UIServiceRequest> serviceRequests = FXCollections.observableArrayList();
  public JFXTreeTableView<UIServiceRequest> serviceTable;
  public AnchorPane mapView;
  public JFXButton submitRequestButton;
  public JFXButton cancelButton;
  public JFXButton cancelOngoing;
  public JFXButton ongoingButton;
  public JFXButton updateButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // columns for table

    // add all location nodes in choicebox

    // priority choices
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

  public void submit(ActionEvent actionEvent)
      throws InstanceNotFoundException, ValidationException {

    // get priority & location
    int priority = Integer.parseInt(choiceBoxPriority.getValue());
    String locationName = choiceBoxLoc.getValue();
    String nodeID = null;
    // check which location
    if (locationName.equals("Intensive Care Unit")) {
      nodeID = "FDEPT00105";
    } else if (locationName.equals("Hemo / Dialysis")) {
      nodeID = "FDEPT00205";
    } else if (locationName.equals("Residents")) {
      nodeID = "FDEPT00405";
    } else if (locationName.equals("Outpatient Infusion Center")) {
      nodeID = "FDEPT00305";
    } else if (locationName.equals("Oncology")) {
      nodeID = "FDEPT00505";
    } else if (locationName.equals("X-ray")) {
      nodeID = "FDEPT00605";
    } else if (locationName.equals("Ambulatory Clinic")) {
      nodeID = "FDEPT01305";
    } else if (locationName.equals("Ambulatory Care Room 1")) {
      nodeID = "FDEPT00705";
    } else if (locationName.equals("Ambulatory Care Room 2")) {
      nodeID = "FDEPT00905";
    } else if (locationName.equals("Ambulatory Care Room 3")) {
      nodeID = "FDEPT01005";
    } else if (locationName.equals("Ambulatory Care Room 4")) {
      nodeID = "FDEPT01105";
    } else if (locationName.equals("Internal Medicine")) {
      nodeID = "FDEPT01405";
    } else if (locationName.equals("Suite 5940")) {
      nodeID = "FSERV00305";
    } else if (locationName.equals("Suite 5945")) {
      nodeID = "FSERV00205";
    } else if (locationName.equals("Suite 51")) {
      nodeID = "FSERV00405";
    } else if (locationName.equals("Suite 58")) {
      nodeID = "FSERV00505";
    } else if (locationName.equals("Ent / Eye Physical / Derm")) {
      nodeID = "FDEPT01605";
    } else if (locationName.equals("Orthopedic")) {
      nodeID = "FDEPT01705";
    } else if (locationName.equals("BWH Surgical Specialties")) {
      nodeID = "FDEPT01805";
    } else if (locationName.equals("Endocrinology/ Diabetes/ Hem-Onc")) {
      nodeID = "FDEPT01505";
    } else if (locationName.equals("Primary Care Physicians")) {
      nodeID = "FDEPT00805";
    }
    // check the type of service requested & date
    String serviceType = choiceBoxType.getValue();
    Date date = new Date(System.currentTimeMillis());
    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    if (serviceType.equals("Security")) {
      SecurityRequest secRequest =
          new SecurityRequest(nodeFactory.read(nodeID), "NOT ASSIGNED", date, priority);
      securityRequestFactory.create(secRequest);

    } else if (serviceType.equals("Maintenance")) {
      MaintenanceRequest maintenanceRequest =
          new MaintenanceRequest(nodeFactory.read(nodeID), "NOT ASSIGNED", date, priority);
      maintenanceRequestFactory.create(maintenanceRequest);
    }

    reset();
  }

  public void cancel(ActionEvent actionEvent) {
    reset();
  }

  public void reset() {
    choiceBoxLoc.setValue(null);
    choiceBoxType.setValue(null);
    choiceBoxPriority.setValue(null);

    textAreaDesc.setText("");
  }

  public void showServiceRequests(ActionEvent actionEvent) {
    submitRequestButton.setVisible(false);
    cancelButton.setVisible(false);

    mapView.setVisible(true);
    cancelOngoing.setVisible(true);
    ongoingButton.setVisible(false);
    updateButton.setVisible(true);

    JFXTreeTableColumn<UIServiceRequest, String> Time =
        new JFXTreeTableColumn<UIServiceRequest, String>("Time");
    Time.setPrefWidth(100);
    Time.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().date;
          }
        });
    JFXTreeTableColumn<UIServiceRequest, String> Priority =
        new JFXTreeTableColumn<UIServiceRequest, String>("Priority");
    Priority.setPrefWidth(100);
    Priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().priority;
          }
        });
    JFXTreeTableColumn<UIServiceRequest, String> Location =
        new JFXTreeTableColumn<UIServiceRequest, String>("Location");
    Location.setPrefWidth(100);
    Location.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().location;
          }
        });
    JFXTreeTableColumn<UIServiceRequest, String> ServiceType =
        new JFXTreeTableColumn<UIServiceRequest, String>("Service Type");
    ServiceType.setPrefWidth(100);
    ServiceType.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().serviceType;
          }
        });
    JFXTreeTableColumn<UIServiceRequest, String> assignee =
        new JFXTreeTableColumn<UIServiceRequest, String>("Assignee");
    assignee.setPrefWidth(100);
    assignee.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().description;
          }
        });
    JFXTreeTableColumn<UIServiceRequest, String> ID =
        new JFXTreeTableColumn<UIServiceRequest, String>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIServiceRequest, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIServiceRequest, String> param) {
            return param.getValue().getValue().id;
          }
        });
    List<MaintenanceRequest> maintenanceRequests =
        maintenanceRequestFactory.getAllMaintenanceRequests();
    if (maintenanceRequests != null) {
      for (int i = 0; i < maintenanceRequests.size(); i++) {
        serviceRequests.add(new UIServiceRequest(maintenanceRequests.get(i)));
      }
    }

    List<SecurityRequest> securityRequests = securityRequestFactory.getAllSecurityRequests();
    if (securityRequests != null) {
      for (int i = 0; i < securityRequests.size(); i++) {
        serviceRequests.add(new UIServiceRequest(securityRequests.get(i)));
      }
    }

    final TreeItem<UIServiceRequest> root =
        new RecursiveTreeItem<UIServiceRequest>(serviceRequests, RecursiveTreeObject::getChildren);

    serviceTable.getColumns().setAll(Time, ID, Priority, ServiceType, Location, assignee);

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    serviceTable.setRoot(root);
    serviceTable.setEditable(true);
    serviceTable.setShowRoot(false);
  }

  public void updateRequests(ActionEvent actionEvent) throws ValidationException {
    for (UIServiceRequest req : serviceRequests) {
      if (req.serviceType.get().equals("Maintenance")
          && req.equals(new UIServiceRequest(maintenanceRequestFactory.read(req.id.get())))) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestFactory.read(req.id.get());
        maintenanceRequest.setDescription(req.description.get());
        maintenanceRequestFactory.update(maintenanceRequest);

      } else if (req.serviceType.get().equals("Security")
          && req.equals(new UIServiceRequest(securityRequestFactory.read(req.id.get())))) {
        SecurityRequest securityRequest = securityRequestFactory.read(req.id.get());
        securityRequest.setDescription(req.description.get());
        securityRequestFactory.update(securityRequest);
      }
      serviceTable.refresh();
    }
  }

  public void backtoInput(ActionEvent actionEvent) {
    submitRequestButton.setVisible(true);
    cancelButton.setVisible(true);

    mapView.setVisible(false);
    cancelOngoing.setVisible(false);
    ongoingButton.setVisible(true);
    updateButton.setVisible(false);
  }
}
