package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Account.Account;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIComputerServiceRequest;
import java.awt.event.MouseEvent;
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
import javafx.util.Callback;
import lombok.SneakyThrows;

public class ComputerServiceController implements Initializable {
  public JFXTreeTableView<UIComputerServiceRequest> treeTableComputer;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXButton requestServiceButton;
  public GridPane servicePane;
  public Label locationLabel;
  public JFXComboBox<String> locationChoice;
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
  public JFXComboBox<String> priorityChoice;
  public AnchorPane mainMenu;
  public AnchorPane checkStatusPane;
  public JFXButton update;
  public GridPane deletePane;
  public JFXTextField deleteText;
  public JFXButton delete;
  public JFXButton backButton;
  public ImageView background;
  public JFXButton checkStatusButton;
  SceneController sceneController = App.getSceneController();

  ObservableList<UIComputerServiceRequest> csrUI = FXCollections.observableArrayList();
  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<ComputerServiceRequest> computerServiceRequests;

  public ComputerServiceController() {
    try {
      computerServiceRequests = databaseManager.getAllComputerServiceRequests();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void handle(MouseEvent mouseEvent) {}

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    background.fitWidthProperty().bind(anchorPane.widthProperty());
    background.fitHeightProperty().bind(anchorPane.heightProperty());
    Account.Type userLevel = databaseManager.getPermissions();
    if (userLevel == Account.Type.USER) {
      checkStatusButton.setDisable(true);

      // set to user
    } else if (userLevel == Account.Type.STAFF || userLevel == Account.Type.ADMIN) {
      checkStatusButton.setDisable(false);
    }
    // set to staff

    // add the different choices to the choicebox
    // Replace this with long names, linked to IDs
    List<Node> nodes = null;
    try {
      nodes = databaseManager.getAllNodes();
    } catch (Exception e) {
      System.out.println(e.getMessage() + e.getClass());
    }
    for (Node node : nodes) {
      locationChoice.getItems().add(node.getId());
    }

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationChoice);

    makeChoice.getItems().add("Apple");
    makeChoice.getItems().add("DELL");
    makeChoice.getItems().add("Lenovo");
    makeChoice.getItems().add("MSI");

    OSChoice.getItems().add("OS");
    OSChoice.getItems().add("Windows 7");
    OSChoice.getItems().add("Windows 8");
    OSChoice.getItems().add("Windows 10");
    OSChoice.getItems().add("Linux");
    OSChoice.getItems().add("Ubuntu");

    issueChoice.getItems().add("Hardware");
    issueChoice.getItems().add("Software");

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");

    // ID
    JFXTreeTableColumn<UIComputerServiceRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UIComputerServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });

    // make column
    JFXTreeTableColumn<UIComputerServiceRequest, String> make = new JFXTreeTableColumn<>("Make");
    make.setPrefWidth(70);
    make.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getMake();
          }
        });
    // OS column
    JFXTreeTableColumn<UIComputerServiceRequest, String> OS =
        new JFXTreeTableColumn<>("Operating System");
    OS.setPrefWidth(110);
    OS.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getOS();
          }
        });
    // type column
    JFXTreeTableColumn<UIComputerServiceRequest, String> type =
        new JFXTreeTableColumn<>("Service Type");
    type.setPrefWidth(90);
    type.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getHardwareSoftware();
          }
        });

    // desc column
    JFXTreeTableColumn<UIComputerServiceRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });
    // priority column
    JFXTreeTableColumn<UIComputerServiceRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });

    ObservableList<String> completedList = FXCollections.observableArrayList();
    completedList.add("Complete");
    completedList.add("Incomplete");

    JFXTreeTableColumn<UIComputerServiceRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(200);
    completed.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) ->
            param.getValue().getValue().getCompleted());
    completed.setCellFactory(
        new Callback<
            TreeTableColumn<UIComputerServiceRequest, String>,
            TreeTableCell<UIComputerServiceRequest, String>>() {
          @Override
          public TreeTableCell<UIComputerServiceRequest, String> call(
              TreeTableColumn<UIComputerServiceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(completedList));
    completed.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIComputerServiceRequest, String>>() {
          @Override
          public void handle(
              TreeTableColumn.CellEditEvent<UIComputerServiceRequest, String> event) {
            TreeItem<UIComputerServiceRequest> current =
                treeTableComputer.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setCompleted(new SimpleStringProperty(event.getNewValue()));
          }
        });

    // Assignee choicebox

    List<Account> employeeNames = databaseManager.getAllAccounts();
    ObservableList<String> employees = FXCollections.observableArrayList();
    for (Account account : employeeNames) {
      employees.add(account.getFirstName());
    }
    JFXTreeTableColumn<UIComputerServiceRequest, String> column =
        new JFXTreeTableColumn<>("Assignee");
    column.setCellValueFactory(
        (JFXTreeTableColumn.CellDataFeatures<UIComputerServiceRequest, String> param) ->
            param.getValue().getValue().getAssignee());
    column.setCellFactory(
        new Callback<
            TreeTableColumn<UIComputerServiceRequest, String>,
            TreeTableCell<UIComputerServiceRequest, String>>() {
          @Override
          public TreeTableCell<UIComputerServiceRequest, String> call(
              TreeTableColumn<UIComputerServiceRequest, String> param) {
            return new TextFieldTreeTableCell<>();
          }
        });
    column.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    column.setCellFactory(ComboBoxTreeTableCell.forTreeTableColumn(employees));
    column.setOnEditCommit(
        new EventHandler<TreeTableColumn.CellEditEvent<UIComputerServiceRequest, String>>() {
          @Override
          public void handle(
              TreeTableColumn.CellEditEvent<UIComputerServiceRequest, String> event) {
            TreeItem<UIComputerServiceRequest> current =
                treeTableComputer.getTreeItem(event.getTreeTablePosition().getRow());
            current.getValue().setAssignee(new SimpleStringProperty(event.getNewValue()));
          }
        });

    // Load the database into the tableview

    for (ComputerServiceRequest csr : computerServiceRequests) {
      csrUI.add(new UIComputerServiceRequest(csr));
    }

    final TreeItem<UIComputerServiceRequest> root =
        new RecursiveTreeItem<UIComputerServiceRequest>(csrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableComputer
        .getColumns()
        .setAll(ID, loc, make, OS, type, desc, priority, completed, column);

    // set as editable

    // assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    // completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableComputer.setRoot(root);
    treeTableComputer.setEditable(true);
    treeTableComputer.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the values
    String location = locationChoice.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node node = databaseManager.readNode(nodeID);
    String make = makeChoice.getValue();
    String issueType = issueChoice.getValue();
    String OS = OSChoice.getValue();
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
    ComputerServiceRequest csRequest =
        new ComputerServiceRequest(
            node, desc, "Not Assigned", date, priorityDB, make, issueType, OS);
    databaseManager.manipulateServiceRequest(csRequest);
    csrUI.add(new UIComputerServiceRequest(csRequest));
    treeTableComputer.refresh();
    descText.setText("");
    OSChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    makeChoice.setValue(null);
    issueChoice.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    OSChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    makeChoice.setValue(null);
    issueChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent) throws Exception {
    for (UIComputerServiceRequest csrui : csrUI) {
      ComputerServiceRequest toUpdate =
          databaseManager.readComputerServiceRequest(csrui.getID().get());
      boolean isSame = csrui.equalsCSR(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(csrui.getAssignee().get());
        String completed = csrui.getCompleted().get();
        if (completed.equals("Complete")) {
          toUpdate.setComplete(true);

        } else if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);
        }
        databaseManager.manipulateServiceRequest(toUpdate);
      }
    }
    treeTableComputer.refresh();
  }

  public void delete(ActionEvent actionEvent) throws Exception {
    String toDelte = deleteText.getText();
    databaseManager.deleteComputerServiceRequest(toDelte);
    csrUI.removeIf(computerServiceRequest -> computerServiceRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    treeTableComputer.refresh();
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    checkStatusPane.setVisible(false);
  }

  public void statusView(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
  }

  //  private void resize(double width) {
  //    System.out.println(width);
  //    Font newFont = new Font(width / 50);
  //    locationLabel.setFont(newFont);
  //    makeLabel.setFont(newFont);
  //    typeLabel.setFont(newFont);
  //    OSLabel.setFont(newFont);
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
