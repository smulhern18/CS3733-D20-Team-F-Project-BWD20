package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.DatabaseManipulators.ComputerServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIComputerServiceRequest;
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
    public JFXTreeTableView<UIComputerServiceRequest> treeTableComputer;
    public Label locLabel;
    public Label languageLabel;
    public Label descLabel;
    public Label issueLabel;
    public JFXButton submit;
    public JFXButton cancel;
    public Label prioLabel;
    public JFXToggleButton switcher;
    public JFXButton update;
    public JFXTextField deleteText;
    public JFXButton delete;
    ObservableList<UIComputerServiceRequest> csrUI = FXCollections.observableArrayList();
    public ChoiceBox<String> locationChoice;
    public ChoiceBox<String> languageChoice;
    public ChoiceBox<String> issueChoice;
    public JFXTextArea descText;
    public ChoiceBox<String> priorityChoice;
    NodeFactory nodeFactory = NodeFactory.getFactory();
    ComputerServiceRequestFactory computerServiceRequest = ComputerServiceRequestFactory.getFactory();
    List<ComputerServiceRequest> computerServiceRequests =
            computerServiceRequest.getAllComputerRequests();


    public void submit(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }

    public void switchView(ActionEvent actionEvent) {
    }

    public void update(ActionEvent actionEvent) {
    }

    public void delete(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
