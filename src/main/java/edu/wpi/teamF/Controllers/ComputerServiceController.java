package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.UIComputerServiceRequest;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeTableCell;

public class ComputerServiceController implements Initializable {
  public JFXTreeTableView<UIComputerServiceRequest> treeTableComputer;
  public Label locLabel;
  public Label makeLabel;
  public Label descLabel;
  public Label issueLabel;
  public JFXButton submit;
  public JFXButton cancel;
  public Label OSLabel;
  public Label prioLabel;
  public JFXToggleButton switcher;
  public JFXButton update;
  ObservableList<UIComputerServiceRequest> csrUI = FXCollections.observableArrayList();
  public ChoiceBox<String> locationChoice;
  public ChoiceBox<String> makeChoice;
  public ChoiceBox<String> issueChoice;
  public ChoiceBox<String> OSChoice;
  public JFXTextArea descText;
  public ChoiceBox<String> priorityChoice;
  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<ComputerServiceRequest> computerServiceRequests =
      databaseManager.getAllComputerServiceRequests();

  public ComputerServiceController() throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getID()));

    // Location column
    JFXTreeTableColumn<UIComputerServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLocation()));

    // make column
    JFXTreeTableColumn<UIComputerServiceRequest, String> make = new JFXTreeTableColumn<>("Make");
    make.setPrefWidth(70);
    make.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getMake()));
    // OS column
    JFXTreeTableColumn<UIComputerServiceRequest, String> OS =
        new JFXTreeTableColumn<>("Operating System");
    OS.setPrefWidth(110);
    OS.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getOS()));
    // type column
    JFXTreeTableColumn<UIComputerServiceRequest, String> type =
        new JFXTreeTableColumn<>("Service Type");
    type.setPrefWidth(90);
    type.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getHardwareSoftware()));
    // desc column
    JFXTreeTableColumn<UIComputerServiceRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getDescription()));
    // priority column
    JFXTreeTableColumn<UIComputerServiceRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getPriority()));
    // assignee column
    JFXTreeTableColumn<UIComputerServiceRequest, String> assignee =
        new JFXTreeTableColumn<>("Assignee");
    assignee.setPrefWidth(80);
    assignee.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getAssignee()));
    // Completed column
    JFXTreeTableColumn<UIComputerServiceRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(70);
    completed.setCellValueFactory(
        cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCompleted()));

    // Load the database into the tableview

    for (ComputerServiceRequest csr : computerServiceRequests) {
      csrUI.add(new UIComputerServiceRequest(csr));
    }

    final TreeItem<UIComputerServiceRequest> root =
        new RecursiveTreeItem<UIComputerServiceRequest>(csrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableComputer
        .getColumns()
        .setAll(ID, loc, make, OS, type, desc, priority, assignee, completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableComputer.setRoot(root);
    treeTableComputer.setEditable(true);
    treeTableComputer.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the values
    String location = locationChoice.getValue();
    Node node = databaseManager.readNode(location);
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

  //  public void update(ActionEvent actionEvent)
  //      throws ValidationException, InstanceNotFoundException {
  //    for (UIComputerServiceRequest csrui : csrUI) {
  //      boolean isSame = csrui.equalsCSR(computerServiceRequest.read(csrui.getID()));
  //      if (!isSame) {
  //        computerServiceRequest.read(csrui.getID()).setAssignee(csrui.getAssignee());
  //        String completed = csrui.getCompleted();
  //        if (completed.equals("F")) {
  //          computerServiceRequest.read(csrui.getID()).setComplete(false);
  //
  //        } else if (completed.equals("T")) {
  //          computerServiceRequest.read(csrui.getID()).setComplete(true);
  //        }
  //      }
  //    }
  //    treeTableComputer.refresh();
  //  }

  public void switchView(ActionEvent actionEvent) {
    boolean isSelected = switcher.isSelected();
    if (isSelected) {
      treeTableComputer.setVisible(true);
      OSLabel.setVisible(false);
      OSChoice.setVisible(false);
      locLabel.setVisible(false);
      locationChoice.setVisible(false);
      makeLabel.setVisible(false);
      makeChoice.setVisible(false);
      descLabel.setVisible(false);
      descText.setVisible(false);
      prioLabel.setVisible(false);
      priorityChoice.setVisible(false);
      submit.setVisible(false);
      cancel.setVisible(false);
      issueLabel.setVisible(false);
      issueChoice.setVisible(false);
      update.setVisible(true);

    } else {
      treeTableComputer.setVisible(false);
      OSLabel.setVisible(true);
      OSChoice.setVisible(true);
      locLabel.setVisible(true);
      locationChoice.setVisible(true);
      makeLabel.setVisible(true);
      makeChoice.setVisible(true);
      descLabel.setVisible(true);
      descText.setVisible(true);
      prioLabel.setVisible(true);
      priorityChoice.setVisible(true);
      submit.setVisible(true);
      cancel.setVisible(true);
      issueLabel.setVisible(true);
      issueChoice.setVisible(true);
      update.setVisible(false);
    }
  }
}
