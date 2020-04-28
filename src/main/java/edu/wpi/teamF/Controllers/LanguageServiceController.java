package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UILanguageServiceRequest;
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
import javafx.util.Callback;
import lombok.SneakyThrows;

public class LanguageServiceController implements Initializable {
  public JFXTreeTableView<UILanguageServiceRequest> table;
  public GridPane servicePane;
  public AnchorPane checkStatusPane;
  public Label locationLabel;
  public Label languageLabel;
  public Label descriptionLabel;
  public Label problemTypeLabel;
  public JFXButton submit;
  public JFXButton cancel;
  public Label priorityLabel;
  public JFXToggleButton switcher;
  public JFXButton update;
  public JFXTextField deleteText;
  public JFXButton delete;
  ObservableList<UILanguageServiceRequest> langUI = FXCollections.observableArrayList();
  public JFXComboBox<String> locationCombobox;
  public JFXComboBox<String> languageCombobox;
  public JFXComboBox<String> problemTypeCombobox;
  public JFXTextArea descriptionText;
  public JFXComboBox<String> priorityCombobox;

  DatabaseManager databaseManager = DatabaseManager.getManager();
  List<LanguageServiceRequest> languageServiceRequests =
      databaseManager.getAllLanguageServiceRequests();

  public LanguageServiceController() throws Exception {}

  public void submit(ActionEvent actionEvent) throws Exception {
    // Get the values
    String location = locationCombobox.getValue();
    String nodeId = location.substring(location.length() - 10);
    Node node = databaseManager.readNode(nodeId);
    String language = languageCombobox.getValue();
    String problemType = problemTypeCombobox.getValue();
    String desc = descriptionText.getText();
    String priority = priorityCombobox.getValue();
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
    LanguageServiceRequest langRequest =
        new LanguageServiceRequest(
            node, desc, "Not Assigned", date, priorityDB, language, problemType);
    databaseManager.manipulateServiceRequest(langRequest);
    langUI.add(new UILanguageServiceRequest(langRequest));
    table.refresh();
    descriptionText.setText("");
    locationCombobox.setValue(null);
    priorityCombobox.setValue(null);
    languageCombobox.setValue(null);
    problemTypeCombobox.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    descriptionText.setText("");
    locationCombobox.setValue(null);
    priorityCombobox.setValue(null);
    languageCombobox.setValue(null);
    problemTypeCombobox.setValue(null);
  }

  public void update(ActionEvent actionEvent) throws Exception {
    for (UILanguageServiceRequest langui : langUI) {
      LanguageServiceRequest toUpdate =
          databaseManager.readLanguageServiceRequest(langui.getID().get());
      boolean isSame = langui.equalsLang(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(langui.getAssignee().get());
        String completed = langui.getCompleted().get();
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

  public void switchView(ActionEvent actionEvent) {
    boolean isSelected = switcher.isSelected();
    if (isSelected) {
      table.setVisible(true);
      locationLabel.setVisible(false);
      locationCombobox.setVisible(false);
      languageLabel.setVisible(false);
      languageCombobox.setVisible(false);
      descriptionLabel.setVisible(false);
      descriptionText.setVisible(false);
      priorityLabel.setVisible(false);
      priorityCombobox.setVisible(false);
      submit.setVisible(false);
      cancel.setVisible(false);
      problemTypeLabel.setVisible(false);
      problemTypeCombobox.setVisible(false);
      update.setVisible(true);
      deleteText.setVisible(true);
      delete.setVisible(true);

    } else {
      table.setVisible(false);
      locationLabel.setVisible(true);
      locationCombobox.setVisible(true);
      languageLabel.setVisible(true);
      languageCombobox.setVisible(true);
      descriptionLabel.setVisible(true);
      descriptionText.setVisible(true);
      priorityLabel.setVisible(true);
      priorityCombobox.setVisible(true);
      submit.setVisible(true);
      cancel.setVisible(true);
      problemTypeLabel.setVisible(true);
      problemTypeCombobox.setVisible(true);
      update.setVisible(false);
      deleteText.setVisible(false);
      delete.setVisible(false);
    }
  }

  public void delete(ActionEvent actionEvent) throws Exception {
    String toDelte = deleteText.getText();
    databaseManager.deleteLanguageServiceRequest(toDelte);
    langUI.removeIf(languageServiceRequest -> languageServiceRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    table.refresh();
  }

  @SneakyThrows
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    List<Node> nodes = null;
    try {
      nodes = databaseManager.getAllNodes();
    } catch (Exception e) {
      System.out.println(e.getMessage() + e.getClass());
    }
    for (Node node : nodes) {
      locationCombobox.getItems().add(node.getId());
    }
    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationCombobox);

    priorityCombobox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));

    problemTypeCombobox.setItems(
        FXCollections.observableArrayList(
            "Require Interpreter", "Require Form Translation", "Require TTY Machine"));

    languageCombobox.setItems(
        FXCollections.observableArrayList("Spanish", "Sign Language", "Other (please describe)"));

    // ID
    JFXTreeTableColumn<UILanguageServiceRequest, String> ID = new JFXTreeTableColumn<>("ID");
    ID.setPrefWidth(100);
    ID.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getID();
          }
        });

    // Location column
    JFXTreeTableColumn<UILanguageServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
    loc.setPrefWidth(100);
    loc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getLocation();
          }
        });

    // language column
    JFXTreeTableColumn<UILanguageServiceRequest, String> language =
        new JFXTreeTableColumn<>("Language");
    language.setPrefWidth(70);
    language.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getLanguage();
          }
        });
    // problemType column
    JFXTreeTableColumn<UILanguageServiceRequest, String> problemType =
        new JFXTreeTableColumn<>("Service Problem Type");
    problemType.setPrefWidth(90);
    problemType.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getProblemType();
          }
        });

    // desc column
    JFXTreeTableColumn<UILanguageServiceRequest, String> desc =
        new JFXTreeTableColumn<>("Description");
    desc.setPrefWidth(80);
    desc.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getDescription();
          }
        });
    // priority column
    JFXTreeTableColumn<UILanguageServiceRequest, String> priority =
        new JFXTreeTableColumn<>("Priority");
    priority.setPrefWidth(50);
    priority.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getPriority();
          }
        });
    // assignee column
    JFXTreeTableColumn<UILanguageServiceRequest, String> assignee =
        new JFXTreeTableColumn<>("Assignee");
    assignee.setPrefWidth(80);
    assignee.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getAssignee();
          }
        });

    JFXTreeTableColumn<UILanguageServiceRequest, String> completed =
        new JFXTreeTableColumn<>("Completed");
    completed.setPrefWidth(80);
    completed.setCellValueFactory(
        new Callback<
            TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String>,
            ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(
              TreeTableColumn.CellDataFeatures<UILanguageServiceRequest, String> param) {
            return param.getValue().getValue().getCompleted();
          }
        });

    // Load the database into the tableview

    for (LanguageServiceRequest lang : languageServiceRequests) {
      langUI.add(new UILanguageServiceRequest(lang));
    }

    final TreeItem<UILanguageServiceRequest> root =
        new RecursiveTreeItem<UILanguageServiceRequest>(langUI, RecursiveTreeObject::getChildren);

    // set the columns for the tableview

    table.getColumns().setAll(ID, loc, language, problemType, desc, priority, assignee, completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    table.setRoot(root);
    table.setEditable(true);
    table.setShowRoot(false);
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(true);
    checkStatusPane.setVisible(false);
  }

  public void checkStatus(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
  }
}
