package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UISecurityRequest;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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

public class SecurityRequestController implements Initializable {
  public GridPane optionBar;
  public AnchorPane mainMenu;
  public Label locationLabel;
  public Label guardsRequestedLabel;
  public Label priorityLabel;
  public Label securityRequestLabel;
  public JFXButton requestServiceButton;
  public JFXButton checkStatusButton;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public JFXButton updateButton;
  public JFXButton deleteButton;
  public JFXTextField deleteText;
  public GridPane deletePane;
  public JFXComboBox<String> locationComboBox;
  public JFXComboBox<String> priorityComboBox;
  public JFXComboBox<String> guardsRequestedComboBox;
  public JFXTreeTableView<UISecurityRequest> table;
  public GridPane servicePane;
  public AnchorPane checkStatusPane;
  public AnchorPane anchorPane;
  public ImageView backgroundImage;

  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<SecurityRequest> securityRequestList = databaseManager.getAllSecurityRequests();

  ObservableList<UISecurityRequest> uiSecurityRequests = FXCollections.observableArrayList();

  public SecurityRequestController() throws Exception {}

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());
    Account.Type userLevel = databaseManager.getPermissions();
    if (userLevel == Account.Type.USER) {
      checkStatusButton.setDisable(true);

      // set to user
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      checkStatusButton.setDisable(false);
    }
    // add the different choices to the choicebox
    // Replace this with long names, linked to IDs

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationComboBox);
    priorityComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
    guardsRequestedComboBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));

    JFXTreeTableColumn<UISecurityRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(param -> param.getValue().getValue().getID());
    JFXTreeTableColumn<UISecurityRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(param -> param.getValue().getValue().getLocation());
    JFXTreeTableColumn<UISecurityRequest, String> priority = new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(100);
    priority.setCellValueFactory(param -> param.getValue().getValue().getPriority());

    JFXTreeTableColumn<UISecurityRequest, String> date = new JFXTreeTableColumn<>("Date");
    date.setPrefWidth(100);
    date.setCellValueFactory(param -> param.getValue().getValue().getDateSubmitted());
    JFXTreeTableColumn<UISecurityRequest, String> guardsRequested =
        new JFXTreeTableColumn<>("Guards Requested");
    guardsRequested.setPrefWidth(100);
    guardsRequested.setCellValueFactory(param -> param.getValue().getValue().getGuardsRequested());

    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Complete");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UISecurityRequest, String> completed = new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UISecurityRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UISecurityRequest, String>,
            TreeTableCell<UISecurityRequest, String>>() {
          @Override
          public TreeTableCell<UISecurityRequest, String> call(
              TreeTableColumn<UISecurityRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UISecurityRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UISecurityRequest, String> event) {
            TreeItem<UISecurityRequest> current =
                table.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    // Assignee choicebox

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      employees.add(account.getFirstName());
    }
    JFXTreeTableColumn<UISecurityRequest, String> column = new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UISecurityRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UISecurityRequest, String>,
            TreeTableCell<UISecurityRequest, String>>() {
          @Override
          public TreeTableCell<UISecurityRequest, String> call(
              TreeTableColumn<UISecurityRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UISecurityRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UISecurityRequest, String> event) {
            TreeItem<UISecurityRequest> current =
                table.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });

    for (SecurityRequest sr : securityRequestList) {
      uiSecurityRequests.add(new UISecurityRequest(sr));
    }
    final TreeItem<UISecurityRequest> root =
        new RecursiveTreeItem<>(uiSecurityRequests, RecursiveTreeObject::getChildren);

    table.getColumns().setAll(ID, date, loc, priority, guardsRequested, column, completed);

    //    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    //    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    table.setRoot(root);
    table.setEditable(true);
    table.setShowRoot(false);

    //    resize(anchorPane.getWidth());
    //    anchorPane
    //        .widthProperty()
    //        .addListener(
    //            (observable, oldWidth, newWidth) -> {
    //              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
    //                resize(newWidth.doubleValue());
    //              }
    //            });
  }

  private void resize(double width) {
    System.out.println(width);
    Font newFont = new Font(width / 50);
    locationLabel.setFont(newFont);
    priorityLabel.setFont(newFont);

    guardsRequestedLabel.setFont(newFont);
    securityRequestLabel.setFont(new Font(width / 20));
    submitButton.setFont(newFont);
    cancelButton.setFont(newFont);
    // deleteButton.setFont(new Font(width / 50));
    updateButton.setFont(newFont);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    String location = locationComboBox.getValue();
    String nodeID = location.substring(location.length() - 10);
    System.out.println(location);
    System.out.println(nodeID);
    Node node = databaseManager.readNode(nodeID);
    String priorityString = priorityComboBox.getValue();
    int priority = 0;
    if (priorityString.equals("Low")) {
      priority = 1;
    } else if (priorityString.equals("Medium")) {
      priority = 2;
    } else if (priorityString.equals("High")) {
      priority = 3;
    }
    String guardsRequestedString = guardsRequestedComboBox.getValue();
    int guardsRequested = 0;
    if (guardsRequestedString.equals("1")) {
      guardsRequested = 1;
    } else if (guardsRequestedString.equals("2")) {
      guardsRequested = 2;
    } else if (guardsRequestedString.equals("3")) {
      guardsRequested = 3;
    } else if (guardsRequestedString.equals("4")) {
      guardsRequested = 4;
    } else if (guardsRequestedString.equals("5")) {
      guardsRequested = 5;
    }

    Date date = new Date(System.currentTimeMillis());
    SecurityRequest securityRequest =
        new SecurityRequest(
            node, "Not Assigned", "No Description", date, priority, guardsRequested);
    databaseManager.manipulateServiceRequest(securityRequest);
    uiSecurityRequests.add(new UISecurityRequest(securityRequest));
    table.refresh();
    resetRequest();
  }

  private void resetRequest() {
    locationComboBox.setValue(null);
    priorityComboBox.setValue(null);
    guardsRequestedComboBox.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    resetRequest();
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    servicePane.toFront();
    checkStatusPane.setVisible(false);
  }

  public void update(ActionEvent actionEvent) throws Exception {
    for (UISecurityRequest uiSR : uiSecurityRequests) {
      SecurityRequest toUpdate = databaseManager.readSecurityRequest(uiSR.getID().get());
      if (!uiSR.equalsCSR(toUpdate)) {
        toUpdate.setAssignee(uiSR.getAssignee().get());
        String completed = uiSR.getCompleted().get();
        if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);
        } else if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    table.refresh();
  }

  public void delete(ActionEvent actionEvent) throws Exception {
    String toDelete = deleteText.getText();
    databaseManager.deleteSecurityRequest(toDelete);
    uiSecurityRequests.removeIf(securityRequest -> securityRequest.getID().get().equals(toDelete));
  }

  public void checkStatus(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
    checkStatusPane.toFront();
  }
}
