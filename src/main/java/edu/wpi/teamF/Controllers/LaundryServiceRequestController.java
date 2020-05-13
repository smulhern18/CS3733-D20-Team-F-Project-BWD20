package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LaundryServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UILaundryServiceRequest;
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

public class LaundryServiceRequestController implements Initializable {
  public JFXTreeTableView<UILaundryServiceRequest> treeTableLaunduary;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public JFXComboBox<String> locationChoice;
  public Label itemsLabel;
  public JFXComboBox<String> itemsChoice;
  public Label quantityLabel;
  public JFXComboBox<String> quantityChoice;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public Label securityRequestLabel;
  public Label temperatureLabel;
  public JFXComboBox<String> temperatureSChoice;
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
  public ImageView backgroundImage;
  public JFXButton checkStatButton;
  public JFXComboBox<String> toDelete;
  SceneController sceneController = App.getSceneController();
  DatabaseManager databaseManager = DatabaseManager.getManager();

  ObservableList<UILaundryServiceRequest> isrUI = FXCollections.observableArrayList();
  List<LaundryServiceRequest> laundryServiceRequests = databaseManager.getAllLaunduaryRequests();

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backgroundImage.fitWidthProperty().bind(anchorPane.widthProperty());
    backgroundImage.fitHeightProperty().bind(anchorPane.heightProperty());
    Account.Type userLevel = databaseManager.getPermissions();
    if (userLevel == Account.Type.USER) {
      checkStatButton.setDisable(true);
      checkStatButton.setVisible(false);

      // set to user
    } else if (userLevel == Account.Type.STAFF) {
      checkStatButton.setDisable(false);
      checkStatButton.setVisible(true);
      delete.setDisable(true);
    } else if (userLevel == Account.Type.ADMIN) {
      checkStatButton.setDisable(false);
      checkStatButton.setVisible(true);
      delete.setDisable(false);
    }

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationChoice);

    //    anchorPane
    //        .widthProperty()
    //        .addListener(
    //            (observable, oldWidth, newWidth) -> {
    //              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
    //                resize(newWidth.doubleValue());
    //              }
    //            });

    itemsChoice.getItems().add("Blankets");
    itemsChoice.getItems().add("Bath Towels");
    itemsChoice.getItems().add("Hand Towels");
    itemsChoice.getItems().add("Wash Cloths");
    itemsChoice.getItems().add("Floor Mats");
    itemsChoice.getItems().add("Patient Gowns");
    itemsChoice.getItems().add("Lab Coats");
    itemsChoice.getItems().add("Scrubs");
    itemsChoice.getItems().add("Medical Uniforms");
    itemsChoice.getItems().add("Sheets");
    itemsChoice.getItems().add("Pillowcases");
    itemsChoice.getItems().add("Other");

    temperatureSChoice.getItems().add("High");
    temperatureSChoice.getItems().add("Medium");
    temperatureSChoice.getItems().add("Low");
    temperatureSChoice.getItems().add("Extremely High");
    temperatureSChoice.getItems().add("Extremely Low");

    quantityChoice.getItems().add("Large");
    quantityChoice.getItems().add("Medium");
    quantityChoice.getItems().add("Small");

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");

    // ID
    JFXTreeTableColumn<UILaundryServiceRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UILaundryServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });

    // items column
    JFXTreeTableColumn<UILaundryServiceRequest, String> items = new JFXTreeTableColumn<>("Items");
    items.setPrefWidth(70);
    items.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getItems();
          }
        });
    // temperature column
    JFXTreeTableColumn<UILaundryServiceRequest, String> temperature =
        new JFXTreeTableColumn<>("Temperature");
    temperature.setPrefWidth(110);
    temperature.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getTemperature();
          }
        });
    // quantity column
    JFXTreeTableColumn<UILaundryServiceRequest, String> quantity =
        new JFXTreeTableColumn<>("Quantity");
    quantity.setPrefWidth(90);
    quantity.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getQuantity();
          }
        });

    // desc column
    JFXTreeTableColumn<UILaundryServiceRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });
    // priority column
    JFXTreeTableColumn<UILaundryServiceRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });

    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Complete");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UILaundryServiceRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UILaundryServiceRequest, String>,
            TreeTableCell<UILaundryServiceRequest, String>>() {
          @Override
          public TreeTableCell<UILaundryServiceRequest, String> call(
              TreeTableColumn<UILaundryServiceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UILaundryServiceRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UILaundryServiceRequest, String> event) {
            TreeItem<UILaundryServiceRequest> current =
                treeTableLaunduary.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      employees.add(account.getFirstName());
    }
    JFXTreeTableColumn<UILaundryServiceRequest, String> column =
        new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UILaundryServiceRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UILaundryServiceRequest, String>,
            TreeTableCell<UILaundryServiceRequest, String>>() {
          @Override
          public TreeTableCell<UILaundryServiceRequest, String> call(
              TreeTableColumn<UILaundryServiceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    // column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UILaundryServiceRequest, String>>() {
          @Override
          public void handle(TreeTableColumn.CellEditEvent<UILaundryServiceRequest, String> event) {
            TreeItem<UILaundryServiceRequest> current =
                treeTableLaunduary.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });

    // Load the database into the tableview

    for (LaundryServiceRequest lsr : laundryServiceRequests) {
      this.isrUI.add(new UILaundryServiceRequest(lsr));
    }
    for (UILaundryServiceRequest yuh : isrUI) {
      toDelete.getItems().add((yuh.getID().get()));
    }

    final TreeItem<UILaundryServiceRequest> root =
        new RecursiveTreeItem<UILaundryServiceRequest>(isrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableLaunduary
        .getColumns()
        .setAll(ID, loc, items, temperature, quantity, desc, priority, column, completed);

    // set as editable

    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableLaunduary.setRoot(root);
    treeTableLaunduary.setEditable(true);
    treeTableLaunduary.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the valuesgit s
    String location = locationChoice.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node node = databaseManager.readNode(nodeID);
    String items = itemsChoice.getValue();
    String quantity = quantityChoice.getValue();
    String temperature = temperatureSChoice.getValue();
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
    LaundryServiceRequest lsRequest =
        new LaundryServiceRequest(
            node, desc, "Not Assigned", date, priorityDB, items, quantity, temperature);
    databaseManager.manipulateServiceRequest(lsRequest);
    isrUI.add(new UILaundryServiceRequest(lsRequest));
    treeTableLaunduary.refresh();
    descText.setText("");
    temperatureSChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    itemsChoice.setValue(null);
    quantityChoice.setValue(null);
    toDelete.getItems().add(lsRequest.getId());
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    temperatureSChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    itemsChoice.setValue(null);
    quantityChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    for (UILaundryServiceRequest lsrUI : isrUI) {
      LaundryServiceRequest toUpdate =
          databaseManager.readLaundryServiceRequest(lsrUI.getID().get());
      boolean isSame = lsrUI.equalsLSR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(lsrUI.getAssignee().get());
        String completed = lsrUI.getCompleted().get();
        if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);

        } else if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    treeTableLaunduary.refresh();
  }

  public void delete(ActionEvent actionEvent) {
    String toDelte = toDelete.getValue();
    databaseManager.deleteLaundryServiceRequest(toDelte);
    isrUI.removeIf(
        launduaryServiceRequest -> launduaryServiceRequest.getID().get().equals(toDelte));
    treeTableLaunduary.refresh();
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
  //    itemsLabel.setFont(newFont);
  //    temperatureLabel.setFont(newFont);
  //    quantityLabel.setFont(newFont);
  //    descLabel.setFont(newFont);
  //    prioLabel.setFont(newFont);
  //    securityRequestLabel.setFont(new Font(width / 20));
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
