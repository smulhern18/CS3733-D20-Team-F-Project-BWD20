package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.ComputerServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.UIComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javax.management.InstanceNotFoundException;

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
  NodeFactory nodeFactory = NodeFactory.getFactory();
  ComputerServiceRequestFactory computerServiceRequest = ComputerServiceRequestFactory.getFactory();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // add the different choices to the choicebox
    List<Node> nodes = nodeFactory.getAllNodes();
    for (Node node : nodes) {
      locationChoice.getItems().add(node.getLongName());
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

    // Location column
    JFXTreeTableColumn<UIComputerServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getLocation()));
    // make column
    JFXTreeTableColumn<UIComputerServiceRequest, String> make = new JFXTreeTableColumn<>("Make");
    make.setPrefWidth(100);
    make.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getMake()));
    // OS column
    JFXTreeTableColumn<UIComputerServiceRequest, String> OS =
        new JFXTreeTableColumn<>("Operation System");
    OS.setPrefWidth(100);
    OS.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getOS()));
    // type column
    JFXTreeTableColumn<UIComputerServiceRequest, String> type =
        new JFXTreeTableColumn<>("Service Type");
    type.setPrefWidth(100);
    type.setCellValueFactory(
        cellData ->
            new ReadOnlyStringWrapper(cellData.getValue().getValue().getHardwareSoftware()));
    // desc column
    JFXTreeTableColumn<UIComputerServiceRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(100);
    desc.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getDescription()));
    // priority column
    JFXTreeTableColumn<UIComputerServiceRequest, String> priority =
        new JFXTreeTableColumn<>("priority");
    priority.setPrefWidth(100);
    priority.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getPriority()));
    // assignee column
    JFXTreeTableColumn<UIComputerServiceRequest, String> assignee =
        new JFXTreeTableColumn<>("Assignee");
    priority.setPrefWidth(100);
    priority.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getAssignee()));
    // Completed column
    JFXTreeTableColumn<UIComputerServiceRequest, String> completed =
        new JFXTreeTableColumn<>("completed");
    priority.setPrefWidth(100);
    priority.setCellValueFactory(
        cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getCompleted()));

    // Load the database into the tableview

    List<ComputerServiceRequest> computerServiceRequests =
        computerServiceRequest.getAllComputerRequests();
    for (ComputerServiceRequest csr : computerServiceRequests) {
      csrUI.add(new UIComputerServiceRequest(csr));
    }

    final TreeItem<UIComputerServiceRequest> root =
        new RecursiveTreeItem<UIComputerServiceRequest>(csrUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    treeTableComputer.getColumns().setAll(loc, make, OS, type, desc, priority, assignee, completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableComputer.setRoot(root);
    treeTableComputer.setEditable(true);
    treeTableComputer.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    // Get the values
    String location = locationChoice.getValue();
    Node node = nodeFactory.read(location);
    String make = makeChoice.getValue();
    String issueType = issueChoice.getValue();
    String OS = OSChoice.getValue();
    String desc = descText.getText();
    String priority = priorityChoice.getValue();
    Date date = new Date(System.currentTimeMillis());
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    ComputerServiceRequest csRequest =
        new ComputerServiceRequest(
            dateFormat.format(date),
            node,
            "Not Assigned",
            desc,
            date,
            Integer.parseInt(priority),
            false,
            make,
            issueType,
            OS);
    computerServiceRequest.create(csRequest);
    csrUI.add(new UIComputerServiceRequest(csRequest));
    treeTableComputer.refresh();
  }

  public void cancel(ActionEvent actionEvent) {
    descText.setText("");
    OSChoice.setValue(null);
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    makeChoice.setValue(null);
    issueChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent) throws ValidationException {
    for (UIComputerServiceRequest csrui : csrUI) {
      ComputerServiceRequest csupdate = computerServiceRequest.read(csrui.getLocation());
      csupdate.setAssignee(csrui.getAssignee());
      csupdate.setComplete(Boolean.parseBoolean(csrui.getCompleted()));
    }
    treeTableComputer.refresh();
  }

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
    }
  }
}
