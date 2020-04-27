package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.FlowerServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.ComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UIComputerServiceRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UiFlowerServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import javax.management.InstanceNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FlowerRequestInfoController {
    public JFXButton requestServiceButton;
    public AnchorPane anchorPane;
    public GridPane optionBar;
    public JFXTreeTableView<UiFlowerServiceRequest> treeTableComputer;
    public GridPane servicePane;
    public Label locationLabel;
    public JFXComboBox<String> locationChoice;
    public JFXButton submitButton;
    public JFXButton cancelButton;
    public Label prioLabel;
    public JFXComboBox<String> priorityChoice;
    public AnchorPane mainMenu;
    public AnchorPane checkStatusPane;
    public JFXButton update;
    public GridPane deletePane;
    public JFXTextField deleteText;
    public JFXButton delete;
    public JFXButton backButton;
    public Label nameLabel;
    public JFXTextField recipientInput;
    public Label roomNumberLabel;
    public JFXTextField roomInput;
    public Label messageLabel;
    public JFXTextArea messsageInput;
    public Label buyerNameLabel;
    public JFXTextField buyerNameInput;
    public Label phoneNumberLabel;
    public JFXTextField phoneNumberInput;
    public JFXCheckBox giftWrapCheckBox;
    SceneController sceneController = App.getSceneController();

    ObservableList<UiFlowerServiceRequest> frUI = FXCollections.observableArrayList();
    NodeFactory nodeFactory = NodeFactory.getFactory();
    FlowerServiceRequestFactory flowerServiceRequest = FlowerServiceRequestFactory.getFactory();
    List<FlowerRequest> flowerServiceRequests =
            flowerServiceRequest.getAllFlowerRequests();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UISetting uiSetting = new UISetting();
        uiSetting.setAsLocationComboBox(locationChoice);

        anchorPane
                .widthProperty()
                .addListener(
                        (observable, oldWidth, newWidth) -> {
                            if (newWidth.doubleValue() != oldWidth.doubleValue()) {
                                resize(newWidth.doubleValue());
                            }
                        });
        // ID
        JFXTreeTableColumn<UiFlowerServiceRequest, String> ID = new JFXTreeTableColumn<>("ID");
        ID.setPrefWidth(100);
        ID.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getID();
                    }
                });
        // Location column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> loc = new JFXTreeTableColumn<>("Location");
        loc.setPrefWidth(100);
        loc.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getLocation();
                    }
                });
        // assignee column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> assignee =
                new JFXTreeTableColumn<>("Assignee");
        assignee.setPrefWidth(80);
        assignee.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getAssignee();
                    }
                });
        // priority column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> priority =
                new JFXTreeTableColumn<>("Priority");
        priority.setPrefWidth(50);
        priority.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getPriority();
                    }
                });
        // recipient name column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> recipientName = new JFXTreeTableColumn<>("Recipient");
        recipientName.setPrefWidth(70);
        recipientName.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getRecipientName();
                    }
                });
        // room number column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> room = new JFXTreeTableColumn<>("Room Number");
        room.setPrefWidth(70);
        room.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getRoomNumber();
                    }
                });
        // bouquet column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> bouquet = new JFXTreeTableColumn<>("Bouquet");
        bouquet.setPrefWidth(70);
        bouquet.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getBouquet();
                    }
                });
        // message column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> message = new JFXTreeTableColumn<>("Message");
        message.setPrefWidth(70);
        message.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getMessage();
                    }
                });
        // buyer name column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> buyerName = new JFXTreeTableColumn<>("Buyer Name");
        buyerName.setPrefWidth(70);
        buyerName.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getBuyerName();
                    }
                });
        // phone number column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> phoneNumber = new JFXTreeTableColumn<>("Phone Number");
        phoneNumber.setPrefWidth(70);
        phoneNumber.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getPhoneNumber();
                    }
                });
        // gift wrap column
        JFXTreeTableColumn<UiFlowerServiceRequest, String> giftWrap = new JFXTreeTableColumn<>("Gift Wrap");
        giftWrap.setPrefWidth(70);
        giftWrap.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getGiftWrap();
                    }
                });
        JFXTreeTableColumn<UiFlowerServiceRequest, String> completed =
                new JFXTreeTableColumn<>("Completed");
        completed.setPrefWidth(80);
        completed.setCellValueFactory(
                new Callback<
                        TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TreeTableColumn.CellDataFeatures<UiFlowerServiceRequest, String> param) {
                        return param.getValue().getValue().getCompleted();
                    }
                });
        // Load the database into the tableview

        for (FlowerRequest fr : flowerServiceRequests) {
            frUI.add(new UiFlowerServiceRequest(fr));
        }

        final TreeItem<UiFlowerServiceRequest> root =
                new RecursiveTreeItem<UiFlowerServiceRequest>(frUI, RecursiveTreeObject::getChildren);

        // set the columns for the tableview

        treeTableComputer
                .getColumns()
                .setAll(ID, loc, assignee, priority, recipientName, room, bouquet, message, buyerName, phoneNumber, giftWrap, completed);

        // set as editable

        assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

        treeTableComputer.setRoot(root);
        treeTableComputer.setEditable(true);
        treeTableComputer.setShowRoot(false);
    }

    public void submit(ActionEvent actionEvent)
            throws ValidationException, InstanceNotFoundException {
        // Get the valuesgit s
        String location = locationChoice.getValue();
        Node node = nodeFactory.read(location);
        String priority = priorityChoice.getValue();
        String recipient = recipientInput.getText();
        String room = roomInput.getText();
       // String bouquet = .getValue();
        String message = messsageInput.getText();
        String buyerName = buyerNameInput.getText();
        String phoneNumber = phoneNumberInput.getText();
        Boolean giftWrap = giftWrapCheckBox.getText();
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
                        ID, location, assignee, priority, recipient, room, bouquet, message, buyerName, phoneNumber, giftWrap, completed));
        flowerServiceRequest.create(fsRequest);
        frUI.add(new UiFlowerServiceRequest(fsRequest));
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

    public void update(ActionEvent actionEvent)
            throws ValidationException, InstanceNotFoundException {
        for (UiFlowerServiceRequest frui : frUI) {
            FlowerRequest toUpdate = flowerServiceRequest.read(frui.getID().get());
            boolean isSame = frui.equalsFR(toUpdate);
            if (!isSame) {
                toUpdate.setAssignee(frui.getAssignee().get());
                String completed = frui.getCompleted().get();
                if (completed.equals("Incomplete")) {
                    toUpdate.setComplete(false);

                } else if (completed.equals("Complete")) {
                    toUpdate.setComplete(true);
                }
                flowerServiceRequest.update(toUpdate);
            }
        }
        treeTableComputer.refresh();
    }

    public void delete(ActionEvent actionEvent) {
        String toDelete = deleteText.getText();
        flowerServiceRequest.delete(toDelete);
        frUI.removeIf(flowerServiceRequest -> flowerServiceRequest.getID().get().equals(toDelete));
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

    private void resize(double width) {
        System.out.println(width);
        Font newFont = new Font(width / 50);
        locationLabel.setFont(newFont);
        nameLabel.setFont(newFont);
        roomNumberLabel.setFont(newFont);
        messageLabel.setFont(newFont);
        buyerNameLabel.setFont(newFont);
        prioLabel.setFont(newFont);
        flowerLabel.setFont(new Font(width / 20));
        submitButton.setFont(newFont);
        cancelButton.setFont(newFont);
        // deleteButton.setFont(new Font(width / 50));
        update.setFont(newFont);
        backButton.setFont(newFont);
    }

    public void backToServiceRequestMain(ActionEvent actionEvent) throws IOException {
        sceneController.switchScene("ServiceRequestMain");
    }

}
