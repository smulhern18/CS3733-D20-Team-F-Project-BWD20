package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.DatabaseManipulators.SecurityRequestFactory;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SecurityRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UISecurityRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javax.management.InstanceNotFoundException;

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

  public AnchorPane anchorPane;
  NodeFactory nodeFactory = NodeFactory.getFactory();
  SecurityRequestFactory securityRequestFactory = SecurityRequestFactory.getFactory();
  List<SecurityRequest> securityRequestList = securityRequestFactory.getAllSecurityRequest();
  ObservableList<UISecurityRequest> uiSecurityRequests = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
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
    JFXTreeTableColumn<UISecurityRequest, String> assignee = new JFXTreeTableColumn<>("Assignee");
    assignee.setPrefWidth(100);
    assignee.setCellValueFactory(param -> param.getValue().getValue().getAssignee());
    JFXTreeTableColumn<UISecurityRequest, String> completed =
        new JFXTreeTableColumn<>("Compeleted");
    completed.setPrefWidth(100);
    completed.setCellValueFactory(param -> param.getValue().getValue().getCompleted());

    for (SecurityRequest sr : securityRequestList) {
      uiSecurityRequests.add(new UISecurityRequest(sr));
    }
    final TreeItem<UISecurityRequest> root =
        new RecursiveTreeItem<>(uiSecurityRequests, RecursiveTreeObject::getChildren);

    table.getColumns().setAll(ID, date, loc, priority, guardsRequested, assignee, completed);

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    table.setRoot(root);
    table.setEditable(true);
    table.setShowRoot(false);

    resize(anchorPane.getWidth());
    anchorPane
        .widthProperty()
        .addListener(
            (observable, oldWidth, newWidth) -> {
              if (newWidth.doubleValue() != oldWidth.doubleValue()) {
                resize(newWidth.doubleValue());
              }
            });
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

  public void submit(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    String location = locationComboBox.getValue();
    String nodeID = location.substring(location.length() - 11);
    String priorityString = priorityComboBox.getValue();
    int priority = 0;
    if (priorityString.equals("Low")) {
      priority = 1;
    } else if (priorityString.equals("Medium")) {
      priority = 2;
    } else if (priorityString.equals("High")) {
      priority = 3;
    }
  }

  public void cancel(ActionEvent actionEvent) {}

  public void request(ActionEvent actionEvent) {}

  public void update(ActionEvent actionEvent) {}

  public void delete(ActionEvent actionEvent) {}

  public void checkStatus(ActionEvent actionEvent) {}
}
