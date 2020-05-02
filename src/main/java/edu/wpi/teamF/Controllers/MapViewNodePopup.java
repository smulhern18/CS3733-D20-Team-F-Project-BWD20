package edu.wpi.teamF.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamF.DatabaseManipulators.DatabaseManager;
import edu.wpi.teamF.ModelClasses.Node;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class MapViewNodePopup implements Initializable {

  @FXML private JFXTextField shortNameInput;

  @FXML private JFXTextField longNameInput;

  @FXML private JFXTextField xCoorInput;

  @FXML private JFXTextField yCoorInput;

  @FXML private JFXTextField buildingInput;

  @FXML private JFXTextField floorInput;

  @FXML private ChoiceBox typeInput;

  @FXML private JFXButton locationButton;

  @FXML private JFXButton addNodeButton;

  @FXML private JFXButton modifyNodeButton;

  @FXML private JFXButton deleteNodeButton;

  @FXML private Label errorLabel;

  private DataMapViewController dataMapViewController;

  private DatabaseManager databaseManager = DatabaseManager.getManager();

  private String id;
  private String shortName;
  private String longName;
  private String building;
  private short xCoord;
  private short yCoord;
  private Node.NodeType type;
  private short floor;

  public MapViewNodePopup(Node node) throws Exception {
    this.id = node.getId();
    this.shortName = node.getShortName();
    this.longName = node.getLongName();
    this.building = node.getBuilding();
    this.xCoord = node.getXCoord();
    this.yCoord = node.getYCoord();
    this.type = node.getType();
    this.floor = node.getFloor();
  }

  public MapViewNodePopup() throws Exception {}

  public MapViewNodePopup(DataMapViewController dataMapViewController) {
    System.out.println("In Constructor" + dataMapViewController);

    this.dataMapViewController = dataMapViewController;
  }

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    typeInput
        .getItems()
        .addAll(
            "CONF", "DEPT", "EXIT", "HALL", "INFO", "LABS", "REST", "RETL", "SERV", "STAF", "STAI");
    typeInput.setValue("CONF");
  }

  @FXML
  private void addNode(ActionEvent event) throws Exception {

    Stage stage;

    int tracker = 0;
    int instance = 0;
    int newInstance = 0;
    int instanceNum = 0;

    // try { // is the input valid?
    short xCoordinate = Short.parseShort(xCoorInput.getText());
    short yCoordinate = Short.parseShort(yCoorInput.getText());
    String building = buildingInput.getText();
    String longName = longNameInput.getText();
    String shortName = shortNameInput.getText();
    Node.NodeType nodeType = Node.NodeType.getEnum(typeInput.getValue().toString());
    short floorNumber = Short.parseShort(floorInput.getText());
    List<Node> typeNodes = databaseManager.getNodesByType(nodeType);

    List<Integer> typeInstances = new ArrayList<>();

    for (int i = 0; i < typeNodes.size(); i++) { // collects all of the instances for the given type
      if (typeNodes.get(i).getFloor() == floorNumber) {
        instanceNum = Integer.parseInt(typeNodes.get(i).getId().substring(5, 8));
        typeInstances.add(instanceNum);
      }
    }

    Collections.sort(typeInstances); // sorts the list

    if (typeNodes.size() > 0) {
      for (int i = 0; i < typeInstances.size(); i++) {
        System.out.println(typeInstances.get(i));
        instance = typeInstances.get(i); // 1
        if (instance - tracker > 1) { // 1-0 = 1
          newInstance = tracker + 1;
          break;
        } else if (instance == typeInstances.size()) {
          newInstance = typeInstances.size() + 1;
          break;
        } else {
          tracker++;
        }
      }
    } else {
      newInstance = 1;
    }
    String strInstance = "" + newInstance;
    String strFloor = "0" + floorNumber;

    switch (strInstance.length()) {
      case 1:
        strInstance = "00" + strInstance;
        break;
      case 2:
        strInstance = "0" + strInstance;
        break;
    }

    String ID = "F" + typeInput.getValue() + strInstance + strFloor;

    Node newNode =
        new Node(
            ID,
            xCoordinate,
            yCoordinate,
            building,
            longName,
            shortName,
            nodeType,
            floorNumber); // creates a new node

    databaseManager.manipulateNode(newNode); // creates the node in the db
    dataMapViewController.drawNode(newNode);
    stage = (Stage) addNodeButton.getScene().getWindow();
    stage.close();

    //    } catch (Exception e) { // throws an error if the input provided by the user is invalid
    //      errorLabel.setText("The input is not valid");
    //    }
  }

  @FXML
  private void modifyNode(ActionEvent event) {}

  @FXML
  private void deleteNode(ActionEvent event) {}

  @FXML
  private void selectLocation(ActionEvent event) {
    Stage stage;
    stage = (Stage) addNodeButton.getScene().getWindow();
    stage.close();
  }

  @FXML
  private void validateNodeText(KeyEvent keyEvent) {
    if (!xCoorInput.getText().isEmpty()
        && !yCoorInput.getText().isEmpty()
        && !buildingInput.getText().isEmpty()
        && !longNameInput.getText().isEmpty()
        && !shortNameInput.getText().isEmpty()
        && !floorInput.getText().isEmpty()) { // ADD TYPE
      modifyNodeButton.setDisable(false);
      modifyNodeButton.setOpacity(1);
      addNodeButton.setDisable(false);
      addNodeButton.setOpacity(1);
    } else {
      modifyNodeButton.setDisable(true);
      modifyNodeButton.setOpacity(.4);
      addNodeButton.setDisable(true);
      addNodeButton.setOpacity(.4);
    }
  }
}
