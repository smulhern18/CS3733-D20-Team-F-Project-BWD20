package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Account.Account.Type;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.MaintenanceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIMaintenenceRequest;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import lombok.SneakyThrows;

public class MaintenanceRequestController implements Initializable {
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public Label makeLabel;
  public JFXComboBox<String> makeChoice;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public Label typeLabel;
  public JFXComboBox<String> issueChoice;
  public Label securityRequestLabel;
  public Label OSLabel;
  public JFXComboBox<String> OSChoice;
  public Label descLabel;
  public JFXTextField descText;
  public Label prioLabel;
  public AnchorPane mainMenu;
  public AnchorPane checkStatusPane;
  public JFXButton update;
  public GridPane deletePane;
  public JFXTextField deleteText;
  public JFXButton delete;
  public JFXButton backButton;
  public JFXButton checkStatusButton;
  public JFXComboBox<String> locationComboBox;
  public Label priorityLabel;
  public JFXComboBox<String> priorityComboBox;
  public JFXComboBox<String> completedComboBox;
  public JFXTextField desText;
  public JFXComboBox<String> assigneeChoice;
  public JFXTreeTableView<UIMaintenenceRequest> treeTableMaintenance;
  public ImageView backgroundImage;
  public JFXComboBox<String> toDelete;
  SceneController sceneController = App.getSceneController();

  ObservableList<UIMaintenenceRequest> csrUI = FXCollections.observableArrayList();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<MaintenanceRequest> maintenanceRequests;

  public MaintenanceRequestController() {
    try {
      maintenanceRequests = databaseManager.getAllMaintenanceRequests();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());

    Account.Type userLevel = databaseManager.getPermissions();
    if (userLevel == Account.Type.USER) {
      checkStatusButton.setDisable(true);

      // set to user
    } else if (userLevel == Account.Type.STAFF) {
      checkStatusButton.setDisable(false);
      delete.setDisable(true);
    } else if (userLevel == Account.Type.ADMIN) {
      checkStatusButton.setDisable(false);
      delete.setDisable(false);
    }
    // add the different choices to the choicebox
    // Replace this with long names, linked to IDs
    List<Node> nodes = null;
    try {
      nodes = databaseManager.getAllNodes();
    } catch (Exception e) {
      System.out.println(e.getMessage() + e.getClass());
    }
    for (Node node : nodes) {
      locationComboBox.getItems().add(node.getId());
    }

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationComboBox);

    priorityComboBox.getItems().add("Low");
    priorityComboBox.getItems().add("Medium");
    priorityComboBox.getItems().add("High");

    List<Account> accounts = databaseManager.getAllAccounts();
    for (Account acc : accounts) {
      if (acc.getType() == Type.JANITOR) {
        assigneeChoice.getItems().add(acc.getFirstName());
      }
    }

    // ID
    JFXTreeTableColumn<UIMaintenenceRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UIMaintenenceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });
    // desc column
    JFXTreeTableColumn<UIMaintenenceRequest, String> desc = new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });
    // priority column
    JFXTreeTableColumn<UIMaintenenceRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });

    // Assignee choicebox

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      if (account.getType() == Account.Type.JANITOR) {
        employees.add(account.getFirstName());
      }
    }
    JFXTreeTableColumn<UIMaintenenceRequest, String> column = new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UIMaintenenceRequest, String>,
            TreeTableCell<UIMaintenenceRequest, String>>() {
          @Override
          public TreeTableCell<UIMaintenenceRequest, String> call(
              TreeTableColumn<UIMaintenenceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIMaintenenceRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UIMaintenenceRequest, String> event) {
            TreeItem<UIMaintenenceRequest> current =
                treeTableMaintenance.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });
    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Complete");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UIMaintenenceRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIMaintenenceRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UIMaintenenceRequest, String>,
            TreeTableCell<UIMaintenenceRequest, String>>() {
          @Override
          public TreeTableCell<UIMaintenenceRequest, String> call(
              TreeTableColumn<UIMaintenenceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIMaintenenceRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UIMaintenenceRequest, String> event) {
            TreeItem<UIMaintenenceRequest> current =
                treeTableMaintenance.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });
    // Load the database into the tableview

    for (MaintenanceRequest csr : maintenanceRequests) {
      csrUI.add(new UIMaintenenceRequest(csr));
    }
    for (UIMaintenenceRequest yuh : csrUI) {
      toDelete.getItems().add((yuh.getID().get()));
    }

    final TreeItem<UIMaintenenceRequest> root =
        new RecursiveTreeItem<UIMaintenenceRequest>(csrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    // treeTableMaintenance.getColumns().setAll(ID, loc, desc, priority, completed, column);
    treeTableMaintenance.getColumns().setAll(ID, loc, desc, priority, completed, column);

    // assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    // completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableMaintenance.setRoot(root);
    treeTableMaintenance.setEditable(true);
    treeTableMaintenance.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the values
    String location = locationComboBox.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node node = databaseManager.readNode(nodeID);
    String desc = desText.getText();
    String priority = priorityComboBox.getValue();
    String assignee = assigneeChoice.getValue();
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
    MaintenanceRequest csRequest =
        new MaintenanceRequest(node, desc, assignee, date, priorityDB, null);
    databaseManager.manipulateServiceRequest(csRequest);
    csrUI.add(new UIMaintenenceRequest(csRequest));
    treeTableMaintenance.refresh();
    locationComboBox.setValue(null);
    priorityComboBox.setValue(null);
    locationComboBox.setValue(null);
    assigneeChoice.setValue(null);
    desText.setText("");
    toDelete.getItems().add(csRequest.getId());
  }

  public void cancel(ActionEvent actionEvent) {
    desText.setText("");
    locationComboBox.setValue(null);
    priorityComboBox.setValue(null);
    issueChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent) throws Exception {
    for (UIMaintenenceRequest csrui : csrUI) {
      MaintenanceRequest toUpdate = databaseManager.readMaintenanceRequest(csrui.getID().get());
      boolean isSame = csrui.equalsCSR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(csrui.getAssignee().get());
        toUpdate.setCompleted(new Date());
        String completed = csrui.getCompleted().get();
        if (completed.equals("Complete")) {
          Date date = new Date();
          toUpdate.setDateTimeSubmitted(date);
          toUpdate.setComplete(true);
        } else if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);
        } else {
          throw new IllegalArgumentException(
              "This doesn't belong in the completed attribute: " + completed);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    treeTableMaintenance.refresh();
  }

  public void delete(ActionEvent actionEvent) throws Exception {
    String toDelte = toDelete.getValue();
    databaseManager.deleteComputerServiceRequest(toDelte);
    csrUI.removeIf(transportRequest -> transportRequest.getID().get().equals(toDelte));
    treeTableMaintenance.refresh();
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

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    locationLabel.setFont(newFont);
    makeLabel.setFont(newFont);
    typeLabel.setFont(newFont);
    OSLabel.setFont(newFont);
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
}
