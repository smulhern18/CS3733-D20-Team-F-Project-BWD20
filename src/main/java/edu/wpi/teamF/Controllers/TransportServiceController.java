package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.TransportRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UITransportRequest;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import lombok.SneakyThrows;

public class TransportServiceController implements Initializable {
  public JFXTreeTableView<UITransportRequest> treeTableTransport;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public JFXComboBox<String> locationChoice;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public Label typeLabel;
  public JFXComboBox<String> issueChoice;
  public Label securityRequestLabel;
  public Label descLabel;
  public JFXTextField descText;
  public Label prioLabel;
  public JFXComboBox<String> priorityChoice;
  public AnchorPane mainMenu;
  public AnchorPane checkStatusPane;
  public JFXButton update;
  public GridPane deletePane;
  public JFXTextField deleteText;
  public JFXButton delete;
  public JFXButton backButton;
  public Label destLabel;
  public JFXButton updateButton;
  public JFXButton deleteButton;
  public JFXComboBox<String> destChoice;
  SceneController sceneController = App.getSceneController();

  ObservableList<UITransportRequest> csrUI = FXCollections.observableArrayList();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<TransportRequest> transportRequests;

  public TransportServiceController() {
    try {
      transportRequests = databaseManager.getAllTransportRequests();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // add the different choices to the choicebox
    // Replace this with long names, linked to ID

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationChoice);
    uiSetting.setAsLocationComboBox(destChoice);

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");


    //Add the types here
    issueChoice.getItems().add("");
    issueChoice.getItems().add("");
    issueChoice.getItems().add("");

    // ID
    JFXTreeTableColumn<UITransportRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UITransportRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UITransportRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UITransportRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UITransportRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UITransportRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });
    // desc column
    JFXTreeTableColumn<UITransportRequest, String> desc = new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UITransportRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });
    // priority column
    JFXTreeTableColumn<UITransportRequest, String> priority = new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UITransportRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UITransportRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });

    // Assignee choicebox

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      employees.add(account.getFirstName());
    }
    JFXTreeTableColumn<UITransportRequest, String> column = new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UITransportRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UITransportRequest, String>,
            TreeTableCell<UITransportRequest, String>>() {
          @Override
          public TreeTableCell<UITransportRequest, String> call(
              TreeTableColumn<UITransportRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UITransportRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UITransportRequest, String> event) {
            TreeItem<UITransportRequest> current =
                treeTableTransport.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });

    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Completed");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UITransportRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UITransportRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UITransportRequest, String>,
            TreeTableCell<UITransportRequest, String>>() {
          @Override
          public TreeTableCell<UITransportRequest, String> call(
              TreeTableColumn<UITransportRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UITransportRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UITransportRequest, String> event) {
            TreeItem<UITransportRequest> current =
                treeTableTransport.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    // Load the database into the tableview

    for (TransportRequest csr : transportRequests) {
      csrUI.add(new UITransportRequest(csr));
    }

    final TreeItem<UITransportRequest> root =
        new RecursiveTreeItem<UITransportRequest>(csrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    //     treeTableComputer
    //             .getColumns()
    //             .setAll(ID, loc, make, OS, type, desc, priority, completed, column);

    // set as editable

    // assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    // completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableTransport.setRoot(root);
    treeTableTransport.setEditable(true);
    treeTableTransport.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the values
    String location = locationChoice.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node nodeL = databaseManager.readNode(nodeID);
    String dest = destChoice.getValue();
    String destID = dest.substring(location.length() - 10);
    Node nodeD = databaseManager.readNode(destID);
    String issueType = issueChoice.getValue();
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
    TransportRequest tsRequest =
        new TransportRequest(nodeL, "Not Assigned", desc, date, priorityDB, issueType, nodeD, null);
    databaseManager.manipulateServiceRequest(tsRequest);
    csrUI.add(new UITransportRequest(tsRequest));
    treeTableTransport.refresh();
    descText.setText("");
    destChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    issueChoice.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    destChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    issueChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent) throws Exception {
    for (UITransportRequest csrui : csrUI) {
      TransportRequest toUpdate = databaseManager.readTransportRequest(csrui.getID().get());
      boolean isSame = csrui.equalsCSR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(csrui.getAssignee().get());
        toUpdate.setDateTimeCompleted(new Date());
        String completed = csrui.getCompleted().get();
        if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
          Date date = new Date();
          toUpdate.setDateTimeCompleted(date);
        } else if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);
        } else {
          throw new IllegalArgumentException(
              "This doesn't belong in the completed attribute: " + completed);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    treeTableTransport.refresh();
  }

  public void delete(ActionEvent actionEvent) throws Exception {
    String toDelte = deleteText.getText();
    databaseManager.deleteComputerServiceRequest(toDelte);
    csrUI.removeIf(transportRequest -> transportRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    treeTableTransport.refresh();
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    checkStatusPane.setVisible(false);
  }

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    locationLabel.setFont(newFont);
    destLabel.setFont(newFont);
    typeLabel.setFont(newFont);
    descLabel.setFont(newFont);
    prioLabel.setFont(newFont);
    securityRequestLabel.setFont(new Font(width / 20));
    submitButton.setFont(newFont);
    cancelButton.setFont(newFont);
    // deleteButton.setFont(new Font(width / 50));
    update.setFont(newFont);
    backButton.setFont(newFont);
  }

  public void backToServiceRequestMain(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void checkStatus(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
  }
}
