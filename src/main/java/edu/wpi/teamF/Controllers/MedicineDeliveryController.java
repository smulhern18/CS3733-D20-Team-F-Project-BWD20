package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.MedicineDeliveryRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIMedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class MedicineDeliveryController implements Initializable {

  public JFXTreeTableView<UIMedicineDeliveryRequest> treeTableMedicine;
  public Label locLabel;
  public Label medicinetypelabel;
  public Label descLabel;
  public Label instructionslabel;
  public JFXButton submit;
  public JFXButton cancel;
  public Label prioLabel;
  public JFXToggleButton switcher;
  public JFXButton update;
  public JFXTextField deleteText;
  public JFXButton delete;
  ObservableList<UIMedicineDeliveryRequest> mdrUI = FXCollections.observableArrayList();
  public ChoiceBox<String> locationChoice;
  public JFXTextArea medicineTypeText;
  public JFXTextArea instructionsText;
  public JFXTextArea descText;
  public ChoiceBox<String> priorityChoice;
  NodeFactory nodeFactory = NodeFactory.getFactory();
  MedicineDeliveryRequestFactory medicineDeliveryRequest =
      MedicineDeliveryRequestFactory.getFactory();
  List<MedicineDeliveryRequest> medicineDeliveryRequests =
      medicineDeliveryRequest.getAllMedicineDeliveryRequests();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // add the different choices to the choicebox
    // Replace this with long names, linked to IDs
    List<Node> nodes = nodeFactory.getAllNodes();
    for (Node node : nodes) {
      locationChoice.getItems().add(node.getId());
    }

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");

    // ID
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> loc =
        new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });

    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> medicineType =
        new JFXTreeTableColumn<>("Medicine Type");
    medicineType.setPrefWidth(80);
    medicineType.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getMedicineType();
          }
        });

    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> instructions =
        new JFXTreeTableColumn<>("Instructions");
    instructions.setPrefWidth(80);
    instructions.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getInstructions();
          }
        });

    // desc column
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });

    // priority column
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });
    // assignee column
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> assignee =
        new JFXTreeTableColumn<>("Assignee");
    assignee.setPrefWidth(80);
    assignee.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getAssignee();
          }
        });

    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(80);
    completed.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) {
            return param.getValue().getValue().getCompleted();
          }
        });

    for (MedicineDeliveryRequest mdr : medicineDeliveryRequests) {
      mdrUI.add(new UIMedicineDeliveryRequest(mdr));
    }

    final TreeItem<UIMedicineDeliveryRequest> root =
        new RecursiveTreeItem<UIMedicineDeliveryRequest>(mdrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableMedicine
        .getColumns()
        .setAll(ID, loc, medicineType, instructions, desc, priority, assignee, completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableMedicine.setRoot(root);
    treeTableMedicine.setEditable(true);
    treeTableMedicine.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    // Get the values
    String location = locationChoice.getValue();
    Node node = nodeFactory.read(location);
    String medicineType = medicineTypeText.getText();
    String instructions = instructionsText.getText();
    String desc = descText.getText();
    String priority = priorityChoice.getValue();
    System.out.println(priority);
    int priorityDB = 1;
    if (priority.equals("Low")) {
      priorityDB = 1;
    } else if (priority.equals("Medium")) {
      priorityDB = 2;
    } else if (priority.equals("High")) {
      priorityDB = 3;
    }
    LocalDateTime now = LocalDateTime.now();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    Date date = new Date(System.currentTimeMillis());
    MedicineDeliveryRequest mdRequest =
        new MedicineDeliveryRequest(
            node, desc, "Not Assigned", date, priorityDB, medicineType, instructions);
    medicineDeliveryRequest.create(mdRequest);
    mdrUI.add(new UIMedicineDeliveryRequest(mdRequest));
    treeTableMedicine.refresh();
    descText.setText("");
    medicineTypeText.setText("");
    instructionsText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    medicineTypeText.setText("");
    instructionsText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    for (UIMedicineDeliveryRequest mdrui : mdrUI) {
      MedicineDeliveryRequest toUpdate = medicineDeliveryRequest.read(mdrui.getID().get());
      boolean isSame = mdrui.equalsMDR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(mdrui.getAssignee().get());
        String completed = mdrui.getCompleted().get();
        if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);

        } else if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
        }
        medicineDeliveryRequest.update(toUpdate);
      }
    }
    treeTableMedicine.refresh();
  }

  public void switchView(ActionEvent actionEvent) {
    boolean isSelected = switcher.isSelected();
    if (isSelected) {
      treeTableMedicine.setVisible(true);
      medicinetypelabel.setVisible(false);
      medicineTypeText.setVisible(false);
      locLabel.setVisible(false);
      locationChoice.setVisible(false);
      instructionslabel.setVisible(false);
      instructionsText.setVisible(false);
      descLabel.setVisible(false);
      descText.setVisible(false);
      prioLabel.setVisible(false);
      priorityChoice.setVisible(false);
      submit.setVisible(false);
      cancel.setVisible(false);
      update.setVisible(true);
      deleteText.setVisible(true);
      delete.setVisible(true);

    } else {
      treeTableMedicine.setVisible(false);
      medicinetypelabel.setVisible(true);
      medicineTypeText.setVisible(true);
      locLabel.setVisible(true);
      locationChoice.setVisible(true);
      instructionslabel.setVisible(true);
      instructionsText.setVisible(true);
      descLabel.setVisible(true);
      descText.setVisible(true);
      prioLabel.setVisible(true);
      priorityChoice.setVisible(true);
      submit.setVisible(true);
      cancel.setVisible(true);
      update.setVisible(false);
      deleteText.setVisible(false);
      delete.setVisible(false);
    }
  }

  public void delete(ActionEvent actionEvent) {
    String toDelte = deleteText.getText();
    medicineDeliveryRequest.delete(toDelte);
    mdrUI.removeIf(
        medicineDeliveryRequest -> medicineDeliveryRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    treeTableMedicine.refresh();
  }
}
