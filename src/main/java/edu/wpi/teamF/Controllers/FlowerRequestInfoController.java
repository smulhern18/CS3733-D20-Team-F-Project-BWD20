/*package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.teamF.App;
import edu.wpi.teamF.Controllers.UISettings.UISetting;
import edu.wpi.teamF.DatabaseManipulators.FlowerServiceRequestFactory;
import edu.wpi.teamF.DatabaseManipulators.NodeFactory;
import edu.wpi.teamF.ModelClasses.Node;
import edu.wpi.teamF.ModelClasses.ServiceRequest.FlowerRequest;
import edu.wpi.teamF.ModelClasses.UIClasses.UiFlowerServiceRequest;
import edu.wpi.teamF.ModelClasses.ValidationException;
import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javax.management.InstanceNotFoundException;

public class FlowerRequestInfoController implements Initializable {

  public JFXButton requestServiceButton;
  public AnchorPane anchorPane;
  public GridPane optionBar;
  public JFXTreeTableView<UiFlowerServiceRequest> treeTableFlower;
  public GridPane servicePane;
  public Label locationLabel;
  public Label flowerRequestLabel;
  public JFXComboBox<String> locationComboBox;
  public JFXButton submitButton;
  public JFXButton cancelButton;
  public Label priorityLabel;
  public JFXComboBox<String> priorityComboBox;
  public AnchorPane mainMenu;
  public AnchorPane checkStatusPane;
  public JFXButton updateButton;
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
  public String bouquet;
  public GridPane flowerPane;
  public ImageView allOccasionPic;
  public ImageView classicDozenPic;
  public ImageView colorfulElegancePic;
  public ImageView contemporaryPic;
  public ImageView dishGardenPic;
  public ImageView grandOrchidsPic;
  public ImageView hollandSpringPic;
  public ImageView orchidFestivalPic;
  public ImageView largeOrchidPic;
  public ImageView sweetSmallPic;
  public ImageView sweetSmall2Pic;
  public ImageView summerSunshinePic;
  public ImageView sunflowerTopiaryPic;
  public ImageView tropicalSplashPic;
  public ImageView babyBoyPic;
  public ImageView babyGirlPic;
  public Label AllOccasionLabel;
  public Label classicDozenLabel;
  public Label colorfulEleganceLabel;
  public Label contemporaryLabel;
  public Label dishGardenLabel;
  public Label grandOrchidsLabel;
  public Label hollandSpringLabel;
  public Label orchidFestivalLabel;
  public Label largeOrchidLabel;
  public Label smallSweetLabel;
  public Label smallSweet2Label;
  public Label summerSunshineLabel;
  public Label sunflowerTopiary;
  public Label tropicalSplashLabel;
  public Label babyBoyLabel;
  public Label babyGirlLabel;
  SceneController sceneController = App.getSceneController();

  ObservableList<UiFlowerServiceRequest> frUI = FXCollections.observableArrayList();
  NodeFactory nodeFactory = NodeFactory.getFactory();
  FlowerServiceRequestFactory flowerServiceRequest = FlowerServiceRequestFactory.getFactory();
  List<FlowerRequest> flowerServiceRequests = flowerServiceRequest.getAllFlowerRequests();

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    UISetting uiSetting = new UISetting();
    uiSetting.setAsLocationComboBox(locationComboBox);

    priorityComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));

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
    assignee.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> recipientName =
        new JFXTreeTableColumn<>("Recipient");
    recipientName.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> room =
        new JFXTreeTableColumn<>("Room Number");
    room.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> choice = new JFXTreeTableColumn<>("Bouquet");
    choice.setPrefWidth(100);
    choice.setCellValueFactory(
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> message =
        new JFXTreeTableColumn<>("Message");
    message.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> buyerName =
        new JFXTreeTableColumn<>("Buyer Name");
    buyerName.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> phoneNumber =
        new JFXTreeTableColumn<>("Phone Number");
    phoneNumber.setPrefWidth(100);
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
    JFXTreeTableColumn<UiFlowerServiceRequest, String> giftWrap =
        new JFXTreeTableColumn<>("Gift Wrap");
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
    // completed column
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
    treeTableFlower
        .getColumns()
        .setAll(
            ID,
            loc,
            assignee,
            priority,
            recipientName,
            room,
            choice,
            message,
            buyerName,
            phoneNumber,
            giftWrap,
            completed);

    // set as editable

    assignee.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    completed.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    priority.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());

    treeTableFlower.setRoot(root);
    treeTableFlower.setEditable(true);
    treeTableFlower.setShowRoot(false);
  }

  public void submit(ActionEvent actionEvent)
      throws ValidationException, InstanceNotFoundException {
    // Get the values git s
    String location = locationComboBox.getValue();
    String nodeID = location.substring(location.length() - 10);
    Node node = nodeFactory.read(nodeID);
    String priority = priorityComboBox.getValue();
    String recipient = recipientInput.getText();
    String room = roomInput.getText();
    // String bouquet = .getValue();
    String message = messsageInput.getText();
    String buyerName = buyerNameInput.getText();
    String phoneNumber = phoneNumberInput.getText();
    Boolean giftWrap = giftWrapCheckBox.selectedProperty().getValue();
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
    String assignee = "Not Assigned";
    FlowerRequest fsRequest =
        new FlowerRequest(
            node,
            assignee,
            "no description",
            date,
            priorityDB,
            recipient,
            room,
            bouquet,
            message,
            buyerName,
            phoneNumber,
            giftWrap);
    flowerServiceRequest.create(fsRequest);
    frUI.add(new UiFlowerServiceRequest(fsRequest));
    treeTableFlower.refresh();
    messsageInput.setText("");
    recipientInput.setText("");
    buyerNameInput.setText("");
    roomInput.setText("");
    giftWrapCheckBox.setSelected(false);
    locationComboBox.setValue(null);
    priorityComboBox.setValue(null);
    servicePane.setVisible(false);
    checkStatusPane.setVisible(false);
    flowerPane.setVisible(true);
    flowerRequestLabel.setVisible(false);
  }

  public void cancel(ActionEvent actionEvent) {
    messsageInput.setText("");
    recipientInput.setText("");
    buyerNameInput.setText("");
    roomInput.setText("");
    phoneNumberInput.setText("");
    giftWrapCheckBox.setSelected(false);
    locationComboBox.setValue(null);
    priorityComboBox.setValue(null);
    servicePane.setVisible(false);
    flowerPane.setVisible(true);
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
    treeTableFlower.refresh();
  }

  public void delete(ActionEvent actionEvent) {
    String toDelete = deleteText.getText();
    flowerServiceRequest.delete(toDelete);
    frUI.removeIf(flowerServiceRequest -> flowerServiceRequest.getID().get().equals(toDelete));
    deleteText.setText("");
    treeTableFlower.refresh();
  }

  public void request(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(false);
    flowerRequestLabel.setVisible(false);
    flowerPane.setVisible(true);
  }

  public void checkStatus(ActionEvent actionEvent) {
    servicePane.setVisible(false);
    checkStatusPane.setVisible(true);
    flowerPane.setVisible(false);
    flowerRequestLabel.setVisible(true);
  }

  private void resize(double width) {
    System.out.println(width);
    babyBoyPic.setScaleX(width / 800);
    babyBoyPic.setScaleY(width / 800);
    babyGirlPic.setScaleX(width / 800);
    babyGirlPic.setScaleY(width / 800);
    tropicalSplashPic.setScaleX(width / 800);
    tropicalSplashPic.setScaleY(width / 800);
    sunflowerTopiaryPic.setScaleX(width / 800);
    sunflowerTopiaryPic.setScaleY(width / 800);
    summerSunshinePic.setScaleX(width / 800);
    summerSunshinePic.setScaleY(width / 800);
    sweetSmall2Pic.setScaleX(width / 800);
    sweetSmall2Pic.setScaleY(width / 800);
    sweetSmallPic.setScaleX(width / 800);
    sweetSmallPic.setScaleY(width / 800);
    largeOrchidPic.setScaleX(width / 800);
    largeOrchidPic.setScaleY(width / 800);
    orchidFestivalPic.setScaleX(width / 800);
    orchidFestivalPic.setScaleY(width / 800);
    hollandSpringPic.setScaleX(width / 800);
    hollandSpringPic.setScaleY(width / 800);
    grandOrchidsPic.setScaleX(width / 800);
    grandOrchidsPic.setScaleY(width / 800);
    dishGardenPic.setScaleX(width / 800);
    dishGardenPic.setScaleY(width / 800);
    contemporaryPic.setScaleX(width / 800);
    contemporaryPic.setScaleY(width / 800);
    colorfulElegancePic.setScaleX(width / 800);
    colorfulElegancePic.setScaleY(width / 800);
    classicDozenPic.setScaleX(width / 800);
    classicDozenPic.setScaleY(width / 800);
    allOccasionPic.setScaleX(width / 800);
    allOccasionPic.setScaleY(width / 800);
    Font newFont = new Font(width / 50);
    locationLabel.setFont(newFont);
    nameLabel.setFont(newFont);
    roomNumberLabel.setFont(newFont);
    messageLabel.setFont(newFont);
    buyerNameLabel.setFont(newFont);
    priorityLabel.setFont(newFont);
    phoneNumberLabel.setFont(newFont);
    flowerRequestLabel.setFont(new Font(width / 30));
    submitButton.setFont(newFont);
    giftWrapCheckBox.setFont(newFont);
    cancelButton.setFont(newFont);
    AllOccasionLabel.setFont(newFont);
    classicDozenLabel.setFont(newFont);
    colorfulEleganceLabel.setFont(newFont);
    contemporaryLabel.setFont(newFont);
    dishGardenLabel.setFont(newFont);
    grandOrchidsLabel.setFont(newFont);
    hollandSpringLabel.setFont(newFont);
    orchidFestivalLabel.setFont(newFont);
    largeOrchidLabel.setFont(newFont);
    smallSweet2Label.setFont(newFont);
    smallSweetLabel.setFont(newFont);
    summerSunshineLabel.setFont(newFont);
    sunflowerTopiary.setFont((newFont));
    tropicalSplashLabel.setFont(newFont);
    babyBoyLabel.setFont(newFont);
    babyGirlLabel.setFont(newFont);
    // deleteButton.setFont(new Font(width / 50));
    updateButton.setFont(newFont);
  }

  public void backToServiceRequestMain(ActionEvent actionEvent) throws IOException {
    sceneController.switchScene("ServiceRequestMain");
  }

  public void AllOccasionClicked(MouseEvent actionEvent) {
    this.bouquet = "All Occasion";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void classicDozenClicked(MouseEvent actionEvent) {
    this.bouquet = "Classic Dozen";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void colorfulEleganceClicked(MouseEvent actionEvent) {
    this.bouquet = "Colorful Elegance";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void contemporaryOrchidsClicked(MouseEvent actionEvent) {
    this.bouquet = "Contemporary Orchids";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void dishGardClicked(MouseEvent actionEvent) {
    this.bouquet = "Dish Garden";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void grandOrchidsClicked(MouseEvent actionEvent) {
    this.bouquet = "Grand Orchids";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void hollandSpringClicked(MouseEvent actionEvent) {
    this.bouquet = "Holland Spring";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void orchidFestivalClicked(MouseEvent actionEvent) {
    this.bouquet = "Orchid Festival";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void largeOrchidClicked(MouseEvent actionEvent) {
    this.bouquet = "Large Orchid Plant";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void smallAndSweetClicked(MouseEvent actionEvent) {
    this.bouquet = "Small and Sweet";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void smallAndSweet2Clicked(MouseEvent actionEvent) {
    this.bouquet = "Small and Sweet II";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void summerSunshineClicked(MouseEvent actionEvent) {
    this.bouquet = "Summer Sunshine";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void sunflowerTopiaryClicked(MouseEvent actionEvent) {
    this.bouquet = "Sunflower Topiary";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void tropicalSplashClicked(MouseEvent actionEvent) {
    this.bouquet = "Tropical Splash";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void welcomeBabyBoyClicked(MouseEvent actionEvent) {
    this.bouquet = "Welcome Baby Boy";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }

  public void welcomeBabyGirlClicked(MouseEvent actionEvent) {
    this.bouquet = "Welcome Baby Girl";
    flowerPane.setVisible(false);
    servicePane.setVisible(true);
    flowerRequestLabel.setVisible(true);
  }
}*/
