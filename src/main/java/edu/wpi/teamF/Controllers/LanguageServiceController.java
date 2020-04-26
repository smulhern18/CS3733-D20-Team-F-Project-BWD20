package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.LanguageServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.LanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UILanguageServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class LanguageServiceController implements Initializable {
  public JFXTreeTableView<UILanguageServiceRequest> treeTableLanguage;
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
  public ChoiceBox<String> locationChoice;
  public ChoiceBox<String> languageChoice;
  public ChoiceBox<String> problemTypeChoice;
  public JFXTextArea descriptionText;
  public ChoiceBox<String> priorityChoice;
  NodeFactory nodeFactory = NodeFactory.getFactory();
  LanguageServiceRequestFactory languageServiceRequest =
      LanguageServiceRequestFactory.getFactory(); // need to do
  List<LanguageServiceRequest> languageServiceRequests =
      languageServiceRequest.getAllLanguageRequests();

  public void submit(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    // Get the values
    String location = locationChoice.getValue();
    Node node = nodeFactory.read(location);
    String language = languageChoice.getValue();
    String problemType = problemTypeChoice.getValue();
    String desc = descriptionText.getText();
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
    LanguageServiceRequest langRequest =
        new LanguageServiceRequest(
            node, desc, "Not Assigned", date, priorityDB, language, problemType);
    languageServiceRequest.create(langRequest);
    langUI.add(new UILanguageServiceRequest(langRequest));
    treeTableLanguage.refresh();
    descriptionText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    languageChoice.setValue(null);
    problemTypeChoice.setValue(null);
  }

  public void cancel(ActionEvent actionEvent) {
    descriptionText.setText("");
    locationChoice.setValue(null);
    priorityChoice.setValue(null);
    languageChoice.setValue(null);
    problemTypeChoice.setValue(null);
  }

  public void update(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    for (UILanguageServiceRequest langui : langUI) {
      LanguageServiceRequest toUpdate = languageServiceRequest.read(langui.getID().get());
      boolean isSame = langui.equalsLang(toUpdate);
      if (!isSame) {
        toUpdate.setAssignee(langui.getAssignee().get());
        String completed = langui.getCompleted().get();
        if (completed.equals("Incomplete")) {
          toUpdate.setComplete(false);

        } else if (completed.equals("Complete")) {
          toUpdate.setComplete(true);
        }
        languageServiceRequest.update(toUpdate);
      }
    }
    treeTableLanguage.refresh();
  }

  public void switchView(ActionEvent actionEvent) {
    boolean isSelected = switcher.isSelected();
    if (isSelected) {
      treeTableLanguage.setVisible(true);
      locationLabel.setVisible(false);
      locationChoice.setVisible(false);
      languageLabel.setVisible(false);
      languageChoice.setVisible(false);
      descriptionLabel.setVisible(false);
      descriptionText.setVisible(false);
      priorityLabel.setVisible(false);
      priorityChoice.setVisible(false);
      submit.setVisible(false);
      cancel.setVisible(false);
      problemTypeLabel.setVisible(false);
      problemTypeChoice.setVisible(false);
      update.setVisible(true);
      deleteText.setVisible(true);
      delete.setVisible(true);

    } else {
      treeTableLanguage.setVisible(false);
      locationLabel.setVisible(true);
      locationChoice.setVisible(true);
      languageLabel.setVisible(true);
      languageChoice.setVisible(true);
      descriptionLabel.setVisible(true);
      descriptionText.setVisible(true);
      priorityLabel.setVisible(true);
      priorityChoice.setVisible(true);
      submit.setVisible(true);
      cancel.setVisible(true);
      problemTypeLabel.setVisible(true);
      problemTypeChoice.setVisible(true);
      update.setVisible(false);
      deleteText.setVisible(false);
      delete.setVisible(false);
    }
  }

  public void delete(ActionEvent actionEvent) {
    String toDelte = deleteText.getText();
    languageServiceRequest.delete(toDelte);
    langUI.removeIf(languageServiceRequest -> languageServiceRequest.getID().get().equals(toDelte));
    deleteText.setText("");
    treeTableLanguage.refresh();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    List<Node> nodes = nodeFactory.getAllNodes();
    for (Node node : nodes) {
      locationChoice.getItems().add(node.getId());
    }

    priorityChoice.getItems().add("Low");
    priorityChoice.getItems().add("Medium");
    priorityChoice.getItems().add("High");

    problemTypeChoice.getItems().add("Require Interpreter");
    problemTypeChoice.getItems().add("Require Form Translation");
    problemTypeChoice.getItems().add("Require TTY Machine");

    languageChoice.getItems().add("Spanish");
    languageChoice.getItems().add("Sign Language");
    languageChoice.getItems().add("Other (please describe)");

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

    treeTableLanguage
        .getColumns()
        .setAll(ID, loc, language, problemType, desc, priority, assignee, completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableLanguage.setRoot(root);
    treeTableLanguage.setEditable(true);
    treeTableLanguage.setShowRoot(false);
  }
}
