package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.SanitationServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.SanitationServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UISanitationServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
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
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SanitationServiceController implements Initializable {
    public JFXTreeTableView<UISanitationServiceRequest> treeTableSanitation;
    public Label locLabel;
    public Label typeLabel;
    public Label descLabel;
    public JFXButton submit;
    public JFXButton cancel;
    public Label priorityLabel;
    public JFXToggleButton switcher;
    public JFXButton update;
    public JFXTextField deleteText;
    public JFXButton delete;
    ObservableList<UISanitationServiceRequest> csrUI = FXCollections.observableArrayList();
    public ChoiceBox<String> locationChoice;
    public ChoiceBox<String> typeChoice;
    public JFXTextArea descText;
    public ChoiceBox<String> priorityChoice;
    NodeFactory nodeFactory = NodeFactory.getFactory();
    SanitationServiceRequestFactory sanitationServiceRequest = SanitationServiceRequestFactory.getFactory();
    List<SanitationServiceRequest> sanitationServiceRequests =
            sanitationServiceRequest.getAllSanitationRequests();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // add the different choices to the choicebox
        // Replace this with long names, linked to IDs
        List<Node> nodes = nodeFactory.getAllNodes();
        for (Node node : nodes) {
            locationChoice.getItems().add(node.getId());
        }

        typeChoice.getItems().add("Spill");
        typeChoice.getItems().add("Odor");
        typeChoice.getItems().add("Cleanliness");

        priorityChoice.getItems().add("Low");
        priorityChoice.getItems().add("Medium");
        priorityChoice.getItems().add("High");

        // ID
        JFXTreeTableColumn<UISanitationServiceRequest, String> ID = new JFXTreeTableColumn<>("ID");
        ID.setPrefWidth(100);
        ID.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getID();
                    }
                });

        // Location column
        JFXTreeTableColumn<UISanitationServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
        loc.setPrefWidth(100);
        loc.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getLocation();
                    }
                });

        // type column
        JFXTreeTableColumn<UISanitationServiceRequest, String> type =
                new JFXTreeTableColumn<>("Sanitation Type");
        type.setPrefWidth(90);
        type.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getType();
                    }
                });

        // desc column
        JFXTreeTableColumn<UISanitationServiceRequest, String> desc =
                new JFXTreeTableColumn<>("Description");
        desc.setPrefWidth(80);
        desc.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getDescription();
                    }
                });
        // priority column
        JFXTreeTableColumn<UISanitationServiceRequest, String> priority =
                new JFXTreeTableColumn<>("Priority");
        priority.setPrefWidth(50);
        priority.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getPriority();
                    }
                });
        // assignee column
        JFXTreeTableColumn<UISanitationServiceRequest, String> assignee =
                new JFXTreeTableColumn<>("Assignee");
        assignee.setPrefWidth(80);
        assignee.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getAssignee();
                    }
                });

        JFXTreeTableColumn<UISanitationServiceRequest, String> completed =
                new JFXTreeTableColumn<>("Completed");
        completed.setPrefWidth(80);
        completed.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UISanitationServiceRequest, String> param) {
                        return param.getValue().getValue().getCompleted();
                    }
                });

        // Load the database into the tableview

        for (SanitationServiceRequest csr : sanitationServiceRequests) {
            csrUI.add(new UISanitationServiceRequest(csr));
        }

        final TreeItem<UISanitationServiceRequest> root =
                new RecursiveTreeItem<UISanitationServiceRequest>(csrUI, RecursiveTreeObject::getChildren);

        // set the columns for the tableview

        treeTableSanitation
                .getColumns()
                .setAll(ID, loc, type, desc, priority, assignee, completed);

        // set as editable

        assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        treeTableSanitation.setRoot(root);
        treeTableSanitation.setEditable(true);
        treeTableSanitation.setShowRoot(false);
    }

    public void submit(ActionEvent actionEvent)
            throws ValidationException, InstanceNotFoundException {
        // Get the values
        String location = locationChoice.getValue();
        Node node = nodeFactory.read(location);
        String type = typeChoice.getValue();
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
        SanitationServiceRequest csRequest =
                new SanitationServiceRequest(node, desc, "Not Assigned", date, priorityDB, type);
        sanitationServiceRequest.create(csRequest);
        csrUI.add(new UISanitationServiceRequest(csRequest));
        treeTableSanitation.refresh();
        descText.setText("");
        locationChoice.setValue(null);
        priorityChoice.setValue(null);
        typeChoice.setValue(null);
    }

    public void cancel(ActionEvent actionEvent) {
        descText.setText("");
        locationChoice.setValue(null);
        priorityChoice.setValue(null);
        typeChoice.setValue(null);
    }

    public void update(ActionEvent actionEvent)
            throws ValidationException, InstanceNotFoundException {
        for (UISanitationServiceRequest csrui : csrUI) {
            SanitationServiceRequest toUpdate = sanitationServiceRequest.read(csrui.getID().get());
            boolean isSame = csrui.equalsCSR(toUpdate);
            if (!isSame) {
                toUpdate.setAssignee(csrui.getAssignee().get());
                String completed = csrui.getCompleted().get();
                if (completed.equals("Incomplete")) {
                    toUpdate.setComplete(false);

                } else if (completed.equals("Complete")) {
                    toUpdate.setComplete(true);
                }
                sanitationServiceRequest.update(toUpdate);
            }
        }
        treeTableSanitation.refresh();
    }

    public void switchView(ActionEvent actionEvent) {
        boolean isSelected = switcher.isSelected();
        if (isSelected) {
            treeTableSanitation.setVisible(true);
            locLabel.setVisible(false);
            locationChoice.setVisible(false);
            descLabel.setVisible(false);
            descText.setVisible(false);
            priorityLabel.setVisible(false);
            priorityChoice.setVisible(false);
            submit.setVisible(false);
            cancel.setVisible(false);
            typeLabel.setVisible(false);
            typeChoice.setVisible(false);
            update.setVisible(true);
            deleteText.setVisible(true);
            delete.setVisible(true);

        } else {
            treeTableSanitation.setVisible(false);
            locLabel.setVisible(true);
            locationChoice.setVisible(true);
            descLabel.setVisible(true);
            descText.setVisible(true);
            priorityLabel.setVisible(true);
            priorityChoice.setVisible(true);
            submit.setVisible(true);
            cancel.setVisible(true);
            typeLabel.setVisible(true);
            typeChoice.setVisible(true);
            update.setVisible(false);
            deleteText.setVisible(false);
            delete.setVisible(false);
        }
    }

    public void delete(ActionEvent actionEvent) {
        String toDelte = deleteText.getText();
        sanitationServiceRequest.delete(toDelte);
        csrUI.removeIf(computerServiceRequest -> computerServiceRequest.getID().get().equals(toDelte));
        deleteText.setText("");
        treeTableSanitation.refresh();
    }
}
