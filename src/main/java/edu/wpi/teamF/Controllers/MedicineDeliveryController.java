package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.DatabaseManipulators.MedicineDeliveryRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIMedicineDeliveryRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class MedicineDeliveryController implements Initializable {
  public JFXTreeTableView<UIMedicineDeliveryRequest> treeTableMedicine;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public JFXComboBox<String> locationChoice;
  public Label medicineLabel;
  public JFXTextField medicineText;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public Label medicineRequestLabel;
  public Label instructionsLabel;
  public JFXTextField instructionsText;
  public Label descLabel;
  public JFXTextField descText;
  public Label priorityLabel;
  public JFXComboBox<String> priorityChoice;
  public AnchorPane mainMenu;
  public AnchorPane checkStatusPane;
  public JFXButton update;
  public GridPane deletePane;
  public JFXTextField deleteText;
  public JFXButton delete;
  public JFXButton backButton;
  SceneController sceneController = App.getSceneController();

  ObservableList<UIMedicineDeliveryRequest> mdrUI = FXCollections.observableArrayList();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  MedicineDeliveryRequestFactory medicineDeliveryRequest = MedicineDeliveryRequestFactory.getFactory();
  List<MedicineDeliveryRequest> medicineDeliveryRequests =
          medicineDeliveryRequest.getAllMedicineDeliveryRequests();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    anchorPane
            .widthProperty()
            .addListener(
                    (observable, oldWidth, newWidth) -> {
                      if (newWidth.doubleValue() != oldWidth.doubleValue()) {
                        resize(newWidth.doubleValue());
                      }
                    });

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
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> loc = new JFXTreeTableColumn<>("Location");
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

    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> medicine =
            new JFXTreeTableColumn<>("Medicine");
    medicine.setPrefWidth(80);
    medicine.setCellValueFactory(
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

    // Load the database into the tableview

    for (MedicineDeliveryRequest mdr : medicineDeliveryRequests) {
      mdrUI.add(new UIMedicineDeliveryRequest(mdr));
    }

    final TreeItem<UIMedicineDeliveryRequest> root =
            new RecursiveTreeItem<UIMedicineDeliveryRequest>(mdrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableMedicine
            .getColumns()
            .setAll(ID, loc, medicine, instructions, desc, priority, assignee, completed);

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
    String medicine = medicineText.getText();
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
                    node, desc, "Not Assigned", date, priorityDB, medicine, instructions);
    medicineDeliveryRequest.create(mdRequest);
    mdrUI.add(new UIMedicineDeliveryRequest(mdRequest));
    treeTableMedicine.refresh();
    descText.setText("");
    medicineText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    instructionsText.setText("");
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    medicineText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    instructionsText.setText("");
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

  public void delete(ActionEvent actionEvent) {
    String toDelte = deleteText.getText();
    medicineDeliveryRequest.delete(toDelte);
    mdrUI.removeIf(medicineDeliveryRequest -> medicineDeliveryRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    treeTableMedicine.refresh();
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    checkStatusPane.setVisible(false);
  }

  public void statusView(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
  }

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    locationLabel.setFont(newFont);
    medicineLabel.setFont(newFont);
    instructionsLabel.setFont(newFont);
    descLabel.setFont(newFont);
    priorityLabel.setFont(newFont);
    medicineRequestLabel.setFont(new Font(width / 20));
    submitButton.setFont(newFont);
    cancelButton.setFont(newFont);
    // deleteButton.setFont(new Font(width / 50));
    update.setFont(newFont);
    backButton.setFont(newFont);
  }

  public void backToServiceRequestMain(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }
}
