package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;
import lombok.SneakyThrows;

public class MedicineDeliveryController implements Initializable {
  public JFXTreeTableView<UIMedicineDeliveryRequest> treeTableMedicine;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public JFXComboBox<String> locationComboBox;
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
  public JFXButton checkButtonButton;
  public ImageView backgroundImage;
  public JFXComboBox<String> toDelete;
  SceneController sceneController = App.getSceneController();

  DatabaseManager databaseManager = DatabaseManager.getManager();
  ObservableList<UIMedicineDeliveryRequest> mdrUI = FXCollections.observableArrayList();
  List<MedicineDeliveryRequest> medicineDeliveryRequests =
      databaseManager.getAllMedicineDeliveryRequests();

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());
    Account.Type userLevel = databaseManager.getPermissions();
    if (userLevel == Account.Type.USER) {
      backButton.setVisible(false);
      backButton.setDisable(true);

      // set to user
    } else if (userLevel == Account.Type.STAFF) {
      backButton.setDisable(false);
      backButton.setVisible(true);
      delete.setDisable(true);
    }else if(userLevel == Account.Type.ADMIN){
      backButton.setDisable(false);
      backButton.setVisible(true);
      backButton.setDisable(false);

    }

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationComboBox);
    //
    //    anchorPane
    //        .widthProperty()
    //        .addListener(
    //            (observable, oldWidth, newWidth) -> {
    //              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
    //                resize(newWidth.doubleValue());
    //              }
    //            });

    List<Node> nodes = null;

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");

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

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      employees.add(account.getFirstName());
    }
    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> column =
        new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UIMedicineDeliveryRequest, String>,
            TreeTableCell<UIMedicineDeliveryRequest, String>>() {
          @Override
          public TreeTableCell<UIMedicineDeliveryRequest, String> call(
              TreeTableColumn<UIMedicineDeliveryRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String>>() {
          @Override
          public void handle(
              TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String> event) {
            TreeItem<UIMedicineDeliveryRequest> current =
                treeTableMedicine.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });

    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Complete");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UIMedicineDeliveryRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIMedicineDeliveryRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UIMedicineDeliveryRequest, String>,
            TreeTableCell<UIMedicineDeliveryRequest, String>>() {
          @Override
          public TreeTableCell<UIMedicineDeliveryRequest, String> call(
              TreeTableColumn<UIMedicineDeliveryRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String>>() {
          @Override
          public void handle(
              TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String> event) {
            TreeItem<UIMedicineDeliveryRequest> current =
                treeTableMedicine.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String>>() {
          @Override
          public void handle(
              TreeTableColumn.CellEditEvent<UIMedicineDeliveryRequest, String> event) {
            TreeItem<UIMedicineDeliveryRequest> current =
                treeTableMedicine.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    for (MedicineDeliveryRequest mdr : medicineDeliveryRequests) {
      mdrUI.add(new UIMedicineDeliveryRequest(mdr));
    }
    for (UIMedicineDeliveryRequest yuh : mdrUI) {
      toDelete.getItems().add((yuh.getID().get()));
    }

    final TreeItem<UIMedicineDeliveryRequest> root =
        new RecursiveTreeItem<UIMedicineDeliveryRequest>(mdrUI, RecursiveTreeObject::getChildren);

    treeTableMedicine
        .getColumns()
        .setAll(ID, loc, medicine, instructions, desc, priority, column, completed);

    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableMedicine.setRoot(root);
    treeTableMedicine.setEditable(true);
    treeTableMedicine.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    String location = locationComboBox.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node node = databaseManager.readNode(nodeID);
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
    databaseManager.manipulateServiceRequest(mdRequest);
    mdrUI.add(new UIMedicineDeliveryRequest(mdRequest));
    treeTableMedicine.refresh();
    descText.setText("");
    medicineText.setText("");
    locationComboBox.setValue(null);
    priorityChoice.setValue(null);
    instructionsText.setText("");
    toDelete.getItems().add(mdRequest.getId());
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    medicineText.setText("");
    locationComboBox.setValue(null);
    priorityChoice.setValue(null);
    instructionsText.setText("");
  }

  public void update(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    for (UIMedicineDeliveryRequest mdrui : mdrUI) {
      MedicineDeliveryRequest toUpdate =
          databaseManager.readMedicineDeliveryService(mdrui.getID().get());
      boolean isSame = mdrui.equalsMDR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(mdrui.getAssignee().get());
        String completed = mdrui.getCompleted().get();
        if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);

        } else if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    treeTableMedicine.refresh();
  }

  public void delete(ActionEvent actionEvent) {
    String toDelte = toDelete.getValue();
    databaseManager.deleteMedicineDeliveryRequest(toDelte);
    mdrUI.removeIf(
        medicineDeliveryRequest -> medicineDeliveryRequest.getID().get().equals(toDelte));
    treeTableMedicine.refresh();
    toDelete.getItems().remove(toDelete.getValue());
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    servicePane.toFront();
    checkStatusPane.setVisible(false);
  }

  public void statusView(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
    checkStatusPane.toFront();
  }

  //  private void resize(double width) {
  //    System.out.println(width);
  //    Font newFont = new Font(width / 50);
  //    locationLabel.setFont(newFont);
  //    medicineLabel.setFont(newFont);
  //    instructionsLabel.setFont(newFont);
  //    descLabel.setFont(newFont);
  //    priorityLabel.setFont(newFont);
  //    medicineRequestLabel.setFont(new Font(width / 20));
  //    submitButton.setFont(newFont);
  //    cancelButton.setFont(newFont);
  //    // deleteButton.setFont(new Font(width / 50));
  //    update.setFont(newFont);
  //    backButton.setFont(newFont);
  //  }

  public void backToServiceRequestMain(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }
}
